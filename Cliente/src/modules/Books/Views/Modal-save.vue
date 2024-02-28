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
                  v-model="book.title"
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
export default {
  name: "ModalSave",
  data() {
    return {
      book: {
        title: "",
        autor: "",
        publishDate: null,
      },
    };
  },

  methods: {
    onClose() {
        this.$bvModal.hide("modal-save");
        this.book.title = "";
        this.book.autor = "";
        this.book.publishDate = null;
    },

    saveBook() {
      console.log(this.book);
    },
  },
};
</script>

<style scoped></style>
