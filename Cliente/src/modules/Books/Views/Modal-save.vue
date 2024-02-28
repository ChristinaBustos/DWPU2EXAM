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
                <label for="libro">Nombre del libor: *</label>
                <b-form-input
                  v-model="book.name"
                  type="text"
                  class="form-control"
                  placeholder="Libro..."
                  required
                  aria-describedby="input-live-help input-live-feedback"
                />
              </b-col>
              <b-col>
                <label for="libro">Autor del libro: *</label>
                <b-form-input
                  v-model="book.autor"
                  type="text"
                  class="form-control"
                  placeholder="Autor..."
                  required
                  aria-describedby="input-live-help input-live-feedback"
                />
              </b-col>
            </b-row>

            <b-row>
                <b-col>
                <label for="pelicula">Fecha de publicación: *</label>
                <b-form-input
                  v-model="book.publishDate"
                  type="date"
                  class="form-control"
                  placeholder="2024..."
                  required
                  aria-describedby="input-live-help input-live-feedback"
                />
              </b-col>
            </b-row>
          </form>
        </main>

        <footer class="text-center mt-5">
          <button class="btn m-1 cancel" @click="onClose" id="savemovie">Cancelar</button>
          <button
            class="btn m-1 success"
            @click="saveBook"
            id="savebook"
            type="submit"
          >
            Registrar
          </button>
        </footer>
      </b-modal>
    </div>
  </div>
</template>

<script>
import Swal from 'sweetalert2';
import axios from 'axios'
export default {
  
  name: "ModalSave",
  data() {
    return {
      book: {
        name: "",
        autor: "",
        publishDate: null,
      },
    };
  },

  methods: {
    onClose() {
        this.$bvModal.hide("modal-save");
        this.book.name = "";
        this.book.autor = "";
        this.book.publishDate = null;
    },

    async saveBook() {
            Swal.fire({
                title: "¿Estás seguro de registrar la pelicula?",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#008c6f',
                cancelButtonColor: '#e11c24',
                confirmButtonText: "Confirmar",
                cancelButtonText: 'Cancelar',
            }).then(async (result) => {
                if (result.isConfirmed) {
                    try {
                        console.log(this.pelicula);
                        await axios.post("http://localhost:8080/api-book/", this.book);
                        Swal.fire({
                            title: "¡Guardado!",
                            text: "El libro se registró correctamente",
                            icon: "success"
                        });
                        this.onClose();
                        this.$emit('book-updated');
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
