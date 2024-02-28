<template>
  <div>
    <div>
      <b-modal hide-footer hide-header centered id="modal-save">
        <header class="text-center border-bottom">
          <p style="font-family: cabin">Registrar Libro</p>
        </header>

        <main>
          <form id="registrarBook">
            <b-row class="lg">
              <b-col>
                <label for="libro">Nombre del Libro: *</label>
                <b-form-input v-model="book.name" type="text" class="form-control" placeholder="Libro..." required
                  aria-describedby="input-live-help input-live-feedback" />
              </b-col>
              <b-col>
                <label for="libro">Autor del libro: *</label>
                <b-form-input v-model="book.autor" type="text" class="form-control" placeholder="Autor..." required
                  aria-describedby="input-live-help input-live-feedback" />
              </b-col>
            </b-row>

            <b-row>
              <b-col cols="6">
                <label for="libro">Fecha de publicación: *</label>
                <b-form-input v-model="book.publishDate" type="date" class="form-control" placeholder="2024..." required
                  aria-describedby="input-live-help input-live-feedback" />
              </b-col>
              <b-col cols="6">
                <label for="libro">Foto Portada: *</label>
                <input type="file" @change="handleFileInputChange">
              </b-col>
            </b-row>
            <b-row>
              <b-col class="my-2 y mx-2">
                <b-card>
                  <b-card-img :src="base64ToImage(book.cover)"></b-card-img>
                </b-card>
              </b-col>
            </b-row>
          </form>
        </main>

        <footer class="text-center mt-5">
          <button class="btn m-1 cancel" @click="onClose" id="savemovie">
            Cancelar
          </button>
          <button class="btn m-1 success" @click="saveBook" id="savebook" type="submit">
            Registrar
          </button>
        </footer>
      </b-modal>
    </div>
  </div>
</template>

<script>
import Swal from "sweetalert2";
import axios from "axios";
export default {
  name: "ModalSave",
  data() {
    return {
      book: {
        name: "",
        autor: "",
        publishDate: null,
        cover: null,
      },
    };
  },

  methods: {
    onClose() {
      this.$bvModal.hide("modal-save");
      this.book.name = "";
      this.book.autor = "";
      this.book.publishDate = null;
      this.book.cover = "";
    },
    base64ToImage(base64String) {
      if (!base64String) {
        console.error('base64String es nulo o indefinido');
        return null; 
      }

      // Extraer el tipo de la imagen desde la cadena Base64
      const type = base64String.substring(
        "data:image/".length,
        base64String.indexOf(";base64")
      );


      // Crear un blob desde la cadena Base64
      const byteCharacters = atob(base64String.split(",")[1]);
      const byteArrays = [];
      for (let offset = 0; offset < byteCharacters.length; offset += 512) {
        const slice = byteCharacters.slice(offset, offset + 512);
        const byteNumbers = new Array(slice.length);
        for (let i = 0; i < slice.length; i++) {
          byteNumbers[i] = slice.charCodeAt(i);
        }
        const byteArray = new Uint8Array(byteNumbers);
        byteArrays.push(byteArray);
      }
      const blob = new Blob(byteArrays, { type: type });

      // Crear una URL para la imagen
      const url = URL.createObjectURL(blob);

      // Retornar la URL de la imagen
      return url;
    },
    handleFileInputChange(event) {
      const file = event.target.files[0];
      if (file) {
        this.imageToBase64(file, (base64String) => { // Cambio a función de flecha
          this.book.cover = base64String; // Accediendo a this.book dentro de la función de flecha
          console.log(this.book.cover)
        });
      }
    },
    imageToBase64(file, callback) {
      const reader = new FileReader();

      reader.onload = function (event) {
        callback(event.target.result);
      };

      reader.onerror = function (error) {
        console.error("Error al leer el archivo:", error);
      };

      reader.readAsDataURL(file);
    },
    async saveBook() {
      Swal.fire({
        title: "¿Estás seguro de registrar la libro?",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#008c6f",
        cancelButtonColor: "#e11c24",
        confirmButtonText: "Confirmar",
        cancelButtonText: "Cancelar",
      }).then(async (result) => {
        if (result.isConfirmed) {
          try {
            console.log(this.libro);
            await axios.post("http://localhost:8080/api-book/", this.book);
            Swal.fire({
              title: "¡Guardado!",
              text: "El libro se registró correctamente",
              icon: "success",
            });
            this.onClose();
            this.$emit("book-updated");
          } catch (error) {
            console.log("Error al guardar el libro", error);
          }
        }
      });
    },
  },
};
</script>

<style>
.success {
  font-family: Cabin;
  background-color: #009475;
  color: white;
}

.cancel {
  font-family: Cabin;
  background-color: #ffce50;
  color: black;
}
</style>