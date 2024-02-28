<template>
  <div>
    <div>
      <b-carousel
        style="text-shadow: 0px 0px 2px #000"
        indicators
        img-width="500"
        img-height="500"
      >
        <b-carousel-slide
          caption="First Slide"
          img-src="https://picsum.photos/1920/480/?image=10"
        ></b-carousel-slide>
        <b-carousel-slide
          caption="Second Slide"
          img-src="https://picsum.photos/1920/480/?image=12"
        ></b-carousel-slide>
        <b-carousel-slide
          caption="Third Slide"
          img-src="https://picsum.photos/1920/480/?image=22"
        ></b-carousel-slide>
        <b-carousel-slide
          caption="Fourth Slide"
          img-src="https://picsum.photos/1920/480/?image=23"
        ></b-carousel-slide>
      </b-carousel>
    </div>
    <br />
    <div>
      <div>
        <b-avatar
          draggable
          @dragstart="handleDragStart"
          @dragover.prevent
          @drop="handleDrop"
        ></b-avatar>
        Registrar libro
      </div>

      <div @dragover.prevent @drop="handleDrop" class="my-5 mx-5">
        <b-card>
          <p style="color: grey" v-if="!isDragging">
            Arrastra acá para guardar la película
          </p>
          <p style="color: grey" v-if="isDragging">
            Suelta aquí para guardar la película
          </p>
        </b-card>
      </div>
    </div>

    <div>
      <b-card class="cardStyle my-5 mx-5">
        <b-card-text>
          <h1>Libros</h1>
          <div>
            <b-col v-for="(libro, index) in paginatedItems" :key="index">
              <b-card :title="libro.name" style="height: 100%; width: auto">
                <b-card-text class="card-text-scroll">
                  <b>Autor:</b> {{ libro.autor }}<br />
                  <b>Fecha publicación:</b> {{ libro.publishDate }}<br />
                </b-card-text>
                <template #footer>
                  <div></div>
                </template>
              </b-card>
            </b-col>

            <div class="text-center" v-if="!paginatedItems.length">
              <p>No se encontraron películas registradas</p>
            </div>
          </div>

          <div class="d-flex justify-content-between align-items-center">
            <div>
              <b-pagination
                v-model="currentPage"
                :total-rows="rows"
                :per-page="perPage"
                aria-controls="my-table"
                class="mt-3"
              ></b-pagination>

              <p class="mt-3">Página actual: {{ currentPage }}</p>
            </div>

            <div class="mb-3">
              <label for="perPageSelect" class="mr-2"
                >Películas por página:</label
              >
              <b-form-select
                v-model="perPage"
                id="perPageSelect"
                :options="perPageOptions"
              ></b-form-select>
            </div>
          </div>
        </b-card-text>
      </b-card>
    </div>

    <ModalSave @book-updated="fetchData" />
    <ModalUpdate
      ref="ModalUpdate"
      :book="selectedBook"
      @book-updated="fetchData"
    />
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
      data: null, // Asegúrate de tener una propiedad books que sea una matriz
      selectedBook: null,
      isDragging: false,
      book: {
        name: "",
        autor: "",
        publishDate: null,
      },
      perPage: 4,
      currentPage: 1,
      perPageOptions: [4, 8, 12, 16],
    };
  },
  computed: {
    rows() {
      return this.data ? this.data.data.length : 0;
    },
    paginatedItems() {
      const start = (this.currentPage - 1) * this.perPage;
      const end = start + this.perPage;
      return this.data ? this.data.data.slice(start, end) : [];
    },
  },
  methods: {
    showModal() {
      this.show = true;
    },
    fetchData() {
      axios
        .get("http://localhost:8080/api-book/")
        .then((response) => {
          this.data = response.data.data; // Asigna la respuesta de la API a books
          console.log(this.data);
        })
        .catch((error) => {
          console.error("Error al obtener datos de la API", error);
        });
    },
    handleDragStart(event) {
      this.$bvModal.show("modal-save");
      console.log("jala el arrastre");
    },
    filterMovies() {
      this.currentPage = 1;
      this.fetchData();
    },
  },
  mounted() {
    this.fetchData();
  },
};
</script>

<style>
.bodybutton {
  text-align: left;
  padding-right: 20px;
  padding-top: 20px;
  padding-bottom: 20px;
}
.bodybuttons {
  text-align: right;
  padding-right: 20px;
  padding-top: 20px;
  padding-bottom: 20px;
}
.cardStyle {
  padding-left: right 50px;
  padding-top: right 20px;
  padding-right: right 50px;
  padding-bottom: right 50px;
}
</style>
