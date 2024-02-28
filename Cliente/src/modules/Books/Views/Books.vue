<template>
  <div>
    <div>
      <b-carousel style="text-shadow: 0px 0px 2px #000" indicators img-width="500" img-height="500">
        <b-carousel-slide caption="First Slide" img-src="https://picsum.photos/1920/480/?image=10"></b-carousel-slide>
        <b-carousel-slide caption="Second Slide" img-src="https://picsum.photos/1920/480/?image=12"></b-carousel-slide>
        <b-carousel-slide caption="Third Slide" img-src="https://picsum.photos/1920/480/?image=22"></b-carousel-slide>
        <b-carousel-slide caption="Fourth Slide" img-src="https://picsum.photos/1920/480/?image=23"></b-carousel-slide>
      </b-carousel>
    </div>

    <div>

      <div class="bodybuttons">
        <b-button v-b-modal.modal-save class="btnadd">
          <b-icon icon="plus"></b-icon> Registrar libro
        </b-button>
      </div>
    </div>


    <div class="d-flex flex-wrap justify-content-around">
      <b-col v-for="(book, index) in data" :key="index" class="d-flex d-fixed">
        <b-card style="height: 100%; width:auto">
          <b-card-title>{{ book.name }}</b-card-title>
          <b-card-sub-title>{{ book.autor }}</b-card-sub-title>
          <b-card-text>{{ book.publishDate }}</b-card-text>
          <template #footer>
            <div class="icono">
              <b-button variant="faded" @click="OpenEditModal(book)"><b-icon icon="pencil"></b-icon></b-button>
              <b-button variant="faded" style="color: red;" @click="deleteBook(book.id)"><b-icon icon="trash"></b-icon></b-button>
            </div>
          </template>
        </b-card>
      </b-col>
    </div>

    <ModalSave @book-updated="fetchData" />
    <ModalUpdate ref="ModalUpdate" :book="selectedBook" @book-updated="fetchData" />
  </div>
</template>

<script>
import ModalSave from "./Modal-save.vue";
import ModalUpdate from "./Modal-update.vue";
import axios from "axios";
import Swal from "sweetalert2";

export default {
  components: { ModalSave, ModalUpdate },
  name: "Books",
  data() {
    return {
      data: null,
      selectedBook: null,
      book: {
        name: "",
        autor: "",
        publishDate: null,
      },
    };
  },
  computed: {

  },
  methods: {
    showModal() {
      this.show = true;
    },
    fetchData() {
      axios
        .get("http://localhost:8080/api-book/")
        .then((response) => {
          this.data = response.data.data;
          console.log(this.data);
        })
        .catch((error) => {
          console.error("Error al obtener datos de la API", error);
        });
    },
    filterMovies() {
      this.currentPage = 1;
      this.fetchData();
    },
    OpenEditModal(book) {
      this.selectedBook = book;
      this.$bvModal.show('modal-update');
    },
    async deleteBook(id) {
      const confirmed = await Swal.fire({
        title: "¿Estás seguro de eliminar el libro?",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#008c6f',
        cancelButtonColor: '#e11c24',
        confirmButtonText: "Sí, eliminar",
        cancelButtonText: 'Cancelar',
      });

      if (confirmed.isConfirmed) {
        try {
          const response = await axios.delete(`http://localhost:8080/api-book/libro/${id}`);
          if (response.data.error) {
            console.error(response.data.message);
          } else {
            Swal.fire({
              title: 'Eliminada',
              text: 'El libro se eliminó correctamente',
              icon: 'success',
              timer: 3000
            });
            this.fetchData();
          }
        } catch (error) {
          const { data } = error;
          this.$swal.fire({
            icon: "error",
            text: data?.text ? data.text : "Error interno",
            timer: 3000,
          });
        }
      }
    },
  },
  mounted() {
    this.fetchData();
  },
};
</script>

<style>
.bodybuttons {
  text-align: right;
  padding-right: 20px;
  padding-top: 20px;
  padding-bottom: 20px;
}

.btnadd {
  background-color: #089779;
}

.cardStyle {
  padding-left: right 50px;
  padding-top: right 20px;
  padding-right: right 50px;
  padding-bottom: right 50px;
}
</style>
