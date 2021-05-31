package br.com.zup.academy.caio.chavepix.cria

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


internal class TipoChavePixTest() {

    @Nested
    inner class ChaveCpfTest{

        @Test
        fun `deve ser valido quando valor for preenchido corretamente para chave cpf`() {
            //Cenario
            val tipoDeChave = TipoChaveRequest.CPF

            //Acao e verificaco
            assertTrue(tipoDeChave.valida("07940813048"))
        }

        @Test
        fun `nao deve ser valido quando valor for preenchido incorretamente para chave cpf`(){
            //Cenario
            val tipoDeChave = TipoChaveRequest.CPF

            //Acao e verificaco
            assertFalse(tipoDeChave.valida("12345678"))
            assertFalse(tipoDeChave.valida(""))
            assertFalse(tipoDeChave.valida(null))
        }


    }

    @Nested
    inner class ChaveEmailTest{

        @Test
        fun `deve ser valido quando valor for preenchido corretamente para chave email`() {
            //Cenario
            val tipoDeChave = TipoChaveRequest.EMAIL

            //Acao e verificaco
            assertTrue(tipoDeChave.valida("email@email.com"))
            assertTrue(tipoDeChave.valida("email@zup.com"))
        }

        @Test
        fun `nao deve ser valido quando valor for preenchido incorretamente para chave email`(){
            //Cenario
            val tipoDeChave = TipoChaveRequest.EMAIL

            //Acao e verificaco
            assertFalse(tipoDeChave.valida("emailteste.com"))
            assertFalse(tipoDeChave.valida(""))
            assertFalse(tipoDeChave.valida(null))
        }


    }

    @Nested
    inner class ChaveCelularTest{

        @Test
        fun `deve ser valido quando valor for preenchido corretamente para chave celular`() {
            //Cenario
            val tipoDeChave = TipoChaveRequest.CELULAR

            //Acao e verificaco
            assertTrue(tipoDeChave.valida("+5599123456789"))
        }

        @Test
        fun `nao deve ser valido quando valor for preenchido incorretamente para chave celular`(){
            //Cenario
            val tipoDeChave = TipoChaveRequest.CELULAR

            //Acao e verificaco
            assertFalse(tipoDeChave.valida("11999996666"))
            assertFalse(tipoDeChave.valida(""))
            assertFalse(tipoDeChave.valida(null))
        }


    }

    @Nested
    inner class ChaveAleatoriaTest {

        @Test
        fun `deve ser valido quando valor for vazio ou nulo para chave aleatoria`() {
            //Cenario
            val tipoDeChave = TipoChaveRequest.ALEATORIA

            //Acao e verificaco
            assertTrue(tipoDeChave.valida(""))
            assertTrue(tipoDeChave.valida(null))
        }

        @Test
        fun `nao deve ser valido quando valor for preenchido para chave aleatoria`(){
            //Cenario
            val tipoDeChave = TipoChaveRequest.ALEATORIA

            //Acao e verificaco
            assertFalse(tipoDeChave.valida("6371717t717t1e71t7"))
        }
    }

}

