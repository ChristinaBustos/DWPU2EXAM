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
            @drop="handleDrop"></b-avatar>
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
      <b-card class="cardStyle">
        <b-card-text>
          <h1>Books</h1>
          <b-button @click="showModal">Add Book</b-button>
        </b-card-text>
      </b-card>
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
      selectedBook: null,
      isDragging: false,
    };
  },
  methods: {
    showModal() {
      this.show = true;
    },
    fetchData() {
      axios
        .get("http://localhost:8080/api-book/")
        .then((response) => {
          this.data = response.data;
        })
        .catch((error) => {
          console.error("Error al obtener datos de la API", error);
        });
    },
    handleDragStart(event) {
        this.$bvModal.show('modal-save');
        console.log("jala el arrastre");
    },
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
