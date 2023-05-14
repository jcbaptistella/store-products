package com.cayena.storeproducts.utils;

public class ErrorMessagesUtils {

    public static final String ERROR_MESSAGE_PRODUCT_ID_NOT_FOUND = "Produto com id = %s não encontrado";
    public static final String ERROR_MESSAGE_DIFFERENT_ID = "O id informado para atualização = %s difere do recebido no objeto para atualizar = %s";
    public static final String ERROR_MESSAGE_QUANTITY_NEGATIVE = "Não é possivel atualizar a quantidade de stock do produto para valores negativo";
    public static final String ERROR_MESSAGE_QUANTITY_NOT_EXISTING = "Quantidade de estoque para o produto não informada";
    public static final String ERROR_MESSAGE_UNIT_PRICE_NOT_EXISTING = "Preço unitario para o produto não informado";
    public static final String ERROR_MESSAGE_PRODUCT_EXISTING = "Produto com o nome = %s já existe";
    public static final String ERROR_MESSAGE_SUPPLIER_NOT_EXISTING = "O fornecedor informado com id = %s não existe";
    public static final String ERROR_MESSAGE_NAME_PRODUCT_NOT_EXISTING = "Nome do produto não foi informado";
    public static final String ERROR_MESSAGE_SUPPLIER_ID_NOT_EXISTING = "Id do fornecedor não foi informado";
}
