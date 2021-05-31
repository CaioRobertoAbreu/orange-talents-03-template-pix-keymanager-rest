package br.com.zup.academy.caio.chavepix.custom_validation

import br.com.zup.academy.caio.chavepix.cria.CriaChavePixRequest
import io.micronaut.core.annotation.AnnotationValue
import io.micronaut.core.annotation.Introspected
import io.micronaut.validation.validator.constraints.ConstraintValidator
import io.micronaut.validation.validator.constraints.ConstraintValidatorContext
import javax.inject.Singleton
import javax.validation.Constraint
import javax.validation.Payload
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.*
import kotlin.reflect.KClass

@MustBeDocumented
@Target(CLASS, TYPE)
@Retention(RUNTIME)
@Constraint(validatedBy = [ChavePixValidator::class])
annotation class ChavePix(
    val message: String = "valor da chave invalido",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = []){

}

@Singleton
class ChavePixValidator: ConstraintValidator<ChavePix, CriaChavePixRequest> {

    override fun isValid(
        value: CriaChavePixRequest?,
        annotationMetadata: AnnotationValue<ChavePix>,
        context: ConstraintValidatorContext): Boolean {

        if(value?.tipoChave == null) {
            context.messageTemplate("Tipo de chave inválido")
            return false
        }

        return value.tipoChave.valida(value.valor!!)
    }

}
