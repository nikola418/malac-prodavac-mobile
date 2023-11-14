package com.triforce.malacprodavac.domain.use_case.product

import com.triforce.malacprodavac.domain.model.CreateProduct
import com.triforce.malacprodavac.domain.model.Product
import com.triforce.malacprodavac.domain.repository.ProductRepository
import java.util.InvalidPropertiesFormatException
import kotlin.jvm.Throws

class AddProduct (

    private val repository: ProductRepository

){

    suspend operator fun invoke(createProduct: CreateProduct){

        if (createProduct.title.isBlank()){
            throw InvalidPropertiesFormatException("Title of the product can't be empty.")
        }

        if(createProduct.desc.isBlank()){
            throw InvalidPropertiesFormatException("Description of the product can't be empty.")
        }

        repository.insertProduct(createProduct)

    }

}