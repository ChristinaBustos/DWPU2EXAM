    <template>
        <div>
            <b-modal hide-footer hide-header centered id="modal-update">
                <header class="text-center border-bottom">
                    <p style="font-family: cabin">Editar libro</p>
                </header>

                <main>
                    <form id="editarLibro" @submit.prevent="updateMovie">
                        <b-row>
                            <b-col>
                                <label for="libro">Nombre del libro: *</label>
                                <b-form-input v-model="editedBook.name" type="text" class="form-control"
                                    placeholder="Libro..."></b-form-input>
                            </b-col>
                            <b-col>
                                <label for="libro">Autor del libro: *</label>
                                <b-form-input v-model="editedBook.autor" type="text" class="form-control"
                                    placeholder="Autor..."></b-form-input>
                            </b-col>
                        </b-row>

                        <b-row>
                            <b-col>
                                <label for="pelicula">Año de publicacion: *</label>
                                <b-form-input v-model="editedBook.publishDate" type="date" class="form-control"
                                    placeholder="2024..." aria-describedby="input-live-help input-live-feedback" />

                            </b-col>
                        </b-row>

                    </form>
                </main>

                <footer class="text-center mt-5">
                    <button class="btn m-1 cancel" @click="onClose" id="savebook">
                        Cancelar
                    </button>
                    <button class="btn m-1 success" @click="updateBook" type="submit">
                        Actualizar
                    </button>
                </footer>
            </b-modal>
        </div>
    </template>

    <script>
    import Swal from 'sweetalert2';
    import axios from 'axios'
    export default {
        name: 'ModalUpdate',
        props: {
            book: Object,
        },
        data() {
            return {
                editedBook: {
                    name: "",
                    autor: "",
                    publishDate: null,
                },
            }
        },
        methods: {
            onClose() {
                this.$bvModal.hide('modal-update');
            },
            async updateBook() {
                try {
                    Swal.fire({
                        title: '¿Estás seguro de actualizar el libro?',
                        icon: 'warning',
                        showCancelButton: true,
                        confirmButtonColor: '#008c6f',
                        cancelButtonColor: '#e11c24',
                        confirmButtonText: 'Confirmar',
                        cancelButtonText: 'Cancelar',
                    }).then(async (result) => {
                        if (result.isConfirmed) {
                            console.log("Libro a editar: ", this.editedBook);
                            await axios.put(`http://localhost:8080/api-book/`, this.editedBook);
                            Swal.fire({
                                title: 'Actualizado',
                                text: 'El libro se actualizó correctamente',
                                icon: 'success',
                            });

                            this.onClose();
                            this.$emit('book-update');
                        }
                    });
                } catch (error) {
                    console.error('Error al actualizar la película', error);
                }
            },
        },
        watch: {
            book: {
                handler(newVal) {
                    this.editedBook = { ...newVal };
                },
                immediate: true,
            },
        },
    }
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