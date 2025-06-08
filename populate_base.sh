#!/bin/bash

# Define a URL base da sua API
API_URL="http://localhost:8080/api/client"

# Define o tipo de conteúdo para as requisições
CONTENT_TYPE="Content-Type: application/json"

# Mensagem de início
echo "Iniciando a inserção de clientes via API..."
echo "API Endpoint: $API_URL"

# --- Clientes para Juros Fixos (18-25, qualquer renda) ---
echo "--- Inserindo clientes para Juros Fixos ---"
curl --location "${API_URL}" \
--header "${CONTENT_TYPE}" \
--data '{ "name": "Ana Silva", "age": 20, "income": 2500.00 }'
echo ""
sleep 0.1

curl --location "${API_URL}" \
--header "${CONTENT_TYPE}" \
--data '{ "name": "Bruno Costa", "age": 24, "income": 4000.00 }'
echo ""
sleep 0.1

curl --location "${API_URL}" \
--header "${CONTENT_TYPE}" \
--data '{ "name": "Carla Dias", "age": 18, "income": 1500.00 }'
echo ""
sleep 0.1

curl --location "${API_URL}" \
--header "${CONTENT_TYPE}" \
--data '{ "name": "Daniel Esteves", "age": 25, "income": 9000.00 }' # Juros Fixos, Variáveis, Hatch, SUV
echo ""
sleep 0.1

curl --location "${API_URL}" \
--header "${CONTENT_TYPE}" \
--data '{ "name": "Eduarda Ferreira", "age": 22, "income": 6000.00 }' # Juros Fixos, Variáveis, Hatch
echo ""
sleep 0.1

# --- Clientes para Juros Variáveis (21-65, renda 5k-15k) ---
echo "--- Inserindo clientes para Juros Variáveis ---"
curl --location "${API_URL}" \
--header "${CONTENT_TYPE}" \
--data '{ "name": "Fernando Garcia", "age": 30, "income": 7000.00 }' # Juros Variáveis, Hatch
echo ""
sleep 0.1

curl --location "${API_URL}" \
--header "${CONTENT_TYPE}" \
--data '{ "name": "Gisele Hermida", "age": 48, "income": 10000.00 }' # Juros Variáveis, Hatch, SUV
echo ""
sleep 0.1

curl --location "${API_URL}" \
--header "${CONTENT_TYPE}" \
--data '{ "name": "Hugo Igor", "age": 55, "income": 6500.00 }' # Juros Variáveis, Hatch
echo ""
sleep 0.1

curl --location "${API_URL}" \
--header "${CONTENT_TYPE}" \
--data '{ "name": "Iara Jorge", "age": 62, "income": 13000.00 }' # Juros Variáveis, Hatch, SUV
echo ""
sleep 0.1

curl --location "${API_URL}" \
--header "${CONTENT_TYPE}" \
--data '{ "name": "João Kelly", "age": 38, "income": 5000.00 }' # Juros Variáveis, Hatch (limite renda)
echo ""
sleep 0.1

curl --location "${API_URL}" \
--header "${CONTENT_TYPE}" \
--data '{ "name": "Karina Lopes", "age": 42, "income": 15000.00 }' # Juros Variáveis, Hatch, SUV (limite renda)
echo ""
sleep 0.1

# --- Clientes para Consignado (> 65, qualquer renda) ---
echo "--- Inserindo clientes para Consignado ---"
curl --location "${API_URL}" \
--header "${CONTENT_TYPE}" \
--data '{ "name": "Laura Martins", "age": 68, "income": 2000.00 }'
echo ""
sleep 0.1

curl --location "${API_URL}" \
--header "${CONTENT_TYPE}" \
--data '{ "name": "Marcos Nunez", "age": 75, "income": 4500.00 }'
echo ""
sleep 0.1

curl --location "${API_URL}" \
--header "${CONTENT_TYPE}" \
--data '{ "name": "Nadia Oliveira", "age": 80, "income": 1000.00 }'
echo ""
sleep 0.1

curl --location "${API_URL}" \
--header "${CONTENT_TYPE}" \
--data '{ "name": "Otávio Pires", "age": 66, "income": 3000.00 }'
echo ""
sleep 0.1

# --- Clientes Não Elegíveis (nenhum dos principais) ---
echo "--- Inserindo clientes Não Elegíveis ---"
curl --location "${API_URL}" \
--header "${CONTENT_TYPE}" \
--data '{ "name": "Paula Quintella", "age": 26, "income": 4999.00 }' # Fora de fixos, renda fora de variáveis
echo ""
sleep 0.1

curl --location "${API_URL}" \
--header "${CONTENT_TYPE}" \
--data '{ "name": "Ricardo Santos", "age": 50, "income": 15001.00 }' # Fora de fixos, renda fora de variáveis, mas elegível para SUV
echo ""
sleep 0.1

curl --location "${API_URL}" \
--header "${CONTENT_TYPE}" \
--data '{ "name": "Sofia Toledo", "age": 29, "income": 3000.00 }' # Fora de fixos, renda fora de variáveis
echo ""
sleep 0.1

curl --location "${API_URL}" \
--header "${CONTENT_TYPE}" \
--data '{ "name": "Tiago Uzeda", "age": 64, "income": 4000.00 }' # Fora de fixos, renda fora de variáveis
echo ""
sleep 0.1

# --- Clientes com Múltiplas Elegibilidades de Crédito Principal ---
echo "--- Inserindo clientes com Múltiplas Elegibilidades (Principais) ---"
curl --location "${API_URL}" \
--header "${CONTENT_TYPE}" \
--data '{ "name": "Vitoria Xavier", "age": 22, "income": 7000.00 }' # Juros Fixos E Variáveis, Hatch
echo ""
sleep 0.1

curl --location "${API_URL}" \
--header "${CONTENT_TYPE}" \
--data '{ "name": "Wallace Yanez", "age": 25, "income": 9000.00 }' # Juros Fixos E Variáveis, Hatch, SUV
echo ""
sleep 0.1

curl --location "${API_URL}" \
--header "${CONTENT_TYPE}" \
--data '{ "name": "Yasmin Zappa", "age": 21, "income": 5500.00 }' # Juros Fixos E Variáveis, Hatch
echo ""
sleep 0.1

# --- Clientes Elegíveis para Crédito Automotivo (Hatch) ---
echo "--- Inserindo clientes para Crédito Automotivo (Hatch) ---"
curl --location "${API_URL}" \
--header "${CONTENT_TYPE}" \
--data '{ "name": "Andreia Barbosa", "age": 35, "income": 7500.00 }' # Juros Variáveis, Hatch
echo ""
sleep 0.1

curl --location "${API_URL}" \
--header "${CONTENT_TYPE}" \
--data '{ "name": "Bruno Carvalho", "age": 40, "income": 14000.00 }' # Juros Variáveis, Hatch, SUV
echo ""
sleep 0.1

curl --location "${API_URL}" \
--header "${CONTENT_TYPE}" \
--data '{ "name": "Cintia Duarte", "age": 50, "income": 5000.00 }' # Juros Variáveis, Hatch (limite da renda)
echo ""
sleep 0.1

# --- Clientes Elegíveis para Crédito Automotivo (SUV) ---
echo "--- Inserindo clientes para Crédito Automotivo (SUV) ---"
curl --location "${API_URL}" \
--header "${CONTENT_TYPE}" \
--data '{ "name": "Diego Fonseca", "age": 33, "income": 9000.00 }' # Juros Variáveis, Hatch, SUV
echo ""
sleep 0.1

curl --location "${API_URL}" \
--header "${CONTENT_TYPE}" \
--data '{ "name": "Elisa Godoy", "age": 21, "income": 8500.00 }' # Juros Fixos (se avaliado primeiro), Hatch, SUV
echo ""
sleep 0.1

curl --location "${API_URL}" \
--header "${CONTENT_TYPE}" \
--data '{ "name": "Fabio Hermosilla", "age": 47, "income": 10000.00 }' # Juros Variáveis, Hatch, SUV
echo ""
sleep 0.1

# --- Clientes Elegíveis para Ambos os Tipos Automotivos (Hatch E SUV) ---
echo "--- Inserindo clientes para Ambos os Tipos Automotivos ---"
curl --location "${API_URL}" \
--header "${CONTENT_TYPE}" \
--data '{ "name": "Giovanna Ibanez", "age": 28, "income": 9500.00 }' # Juros Variáveis, Hatch, SUV
echo ""
sleep 0.1

curl --location "${API_URL}" \
--header "${CONTENT_TYPE}" \
--data '{ "name": "Heitor Junqueira", "age": 39, "income": 11000.00 }' # Juros Variáveis, Hatch, SUV
echo ""
sleep 0.1

curl --location "${API_URL}" \
--header "${CONTENT_TYPE}" \
--data '{ "name": "Julia Klein", "age": 41, "income": 14500.00 }' # Juros Variáveis, Hatch, SUV
echo ""
sleep 0.1

# --- Clientes para o Endpoint Adicional (23-49, Juros Fixos, Hatch) ---
echo "--- Inserindo clientes para Endpoint Adicional ---"
curl --location "${API_URL}" \
--header "${CONTENT_TYPE}" \
--data '{ "name": "Katia Leal", "age": 24, "income": 7000.00 }' # Juros Fixos, Variáveis, Hatch, SUV (também se encaixa)
echo ""
sleep 0.1

curl --location "${API_URL}" \
--header "${CONTENT_TYPE}" \
--data '{ "name": "Leonardo Meireles", "age": 23, "income": 6000.00 }' # Juros Fixos, Variáveis, Hatch
echo ""
sleep 0.1

curl --location "${API_URL}" \
--header "${CONTENT_TYPE}" \
--data '{ "name": "Monica Novaes", "age": 30, "income": 8000.00 }' # Juros Variáveis, Hatch
echo ""
sleep 0.1

curl --location "${API_URL}" \
--header "${CONTENT_TYPE}" \
--data '{ "name": "Nelson Ortiz", "age": 49, "income": 12000.00 }' # Juros Variáveis, Hatch, SUV (limite da idade)
echo ""
sleep 0.1

curl --location "${API_URL}" \
--header "${CONTENT_TYPE}" \
--data '{ "name": "Olivia Pavan", "age": 37, "income": 9000.00 }' # Juros Variáveis, Hatch, SUV
echo ""
sleep 0.1

# --- Mais alguns clientes diversos (totalizando 40) ---
echo "--- Inserindo clientes adicionais diversos ---"
curl --location "${API_URL}" \
--header "${CONTENT_TYPE}" \
--data '{ "name": "Paulo Queiroz", "age": 58, "income": 4000.00 }' # Não elegível
echo ""
sleep 0.1

curl --location "${API_URL}" \
--header "${CONTENT_TYPE}" \
--data '{ "name": "Quiteria Rangel", "age": 20, "income": 10000.00 }' # Juros Fixos, Hatch, SUV
echo ""
sleep 0.1

curl --location "${API_URL}" \
--header "${CONTENT_TYPE}" \
--data '{ "name": "Renato Silva", "age": 65, "income": 5000.00 }' # Juros Variáveis, Hatch (limite da idade/renda)
echo ""
sleep 0.1

curl --location "${API_URL}" \
--header "${CONTENT_TYPE}" \
--data '{ "name": "Susana Telles", "age": 70, "income": 9000.00 }' # Consignado, Hatch, SUV
echo ""
sleep 0.1

curl --location "${API_URL}" \
--header "${CONTENT_TYPE}" \
--data '{ "name": "Tarcisio Uchoa", "age": 27, "income": 10000.00 }' # Juros Variáveis, Hatch, SUV
echo ""
sleep 0.1

curl --location "${API_URL}" \
--header "${CONTENT_TYPE}" \
--data '{ "name": "Ursula Viana", "age": 31, "income": 16000.00 }' # Não elegível para principal, SUV
echo ""
sleep 0.1

curl --location "${API_URL}" \
--header "${CONTENT_TYPE}" \
--data '{ "name": "Valdir Werner", "age": 44, "income": 4500.00 }' # Não elegível
echo ""
sleep 0.1

curl --location "${API_URL}" \
--header "${CONTENT_TYPE}" \
--data '{ "name": "Xenia Ximenes", "age": 59, "income": 13000.00 }' # Juros Variáveis, Hatch, SUV
echo ""
sleep 0.1

curl --location "${API_URL}" \
--header "${CONTENT_TYPE}" \
--data '{ "name": "Yago Zaccarias", "age": 19, "income": 3000.00 }' # Juros Fixos
echo ""
sleep 0.1

curl --location "${API_URL}" \
--header "${CONTENT_TYPE}" \
--data '{ "name": "Zelia Almeida", "age": 26, "income": 4000.00 }' # Não elegível
echo ""
sleep 0.1

# --- Novos 10 Clientes adicionais (totalizando 50) ---
echo "--- Inserindo mais 10 clientes para completar 50 ---"
curl --location "${API_URL}" \
--header "${CONTENT_TYPE}" \
--data '{ "name": "Aline Barroso", "age": 40, "income": 14000.00 }' # Juros Variáveis, Hatch, SUV
echo ""
sleep 0.1

curl --location "${API_URL}" \
--header "${CONTENT_TYPE}" \
--data '{ "name": "Bernardo Campos", "age": 50, "income": 6000.00 }' # Juros Variáveis, Hatch
echo ""
sleep 0.1

curl --location "${API_URL}" \
--header "${CONTENT_TYPE}" \
--data '{ "name": "Cecilia Duarte", "age": 60, "income": 15000.00 }' # Juros Variáveis, Hatch, SUV
echo ""
sleep 0.1

curl --location "${API_URL}" \
--header "${CONTENT_TYPE}" \
--data '{ "name": "Denise Fontes", "age": 23, "income": 1000.00 }' # Juros Fixos
echo ""
sleep 0.1

curl --location "${API_URL}" \
--header "${CONTENT_TYPE}" \
--data '{ "name": "Elias Guedes", "age": 72, "income": 12000.00 }' # Consignado, Hatch, SUV
echo ""
sleep 0.1

curl --location "${API_URL}" \
--header "${CONTENT_TYPE}" \
--data '{ "name": "Gustavo Henriques", "age": 26, "income": 7000.00 }' # Juros Variáveis, Hatch
echo ""
sleep 0.1

curl --location "${API_URL}" \
--header "${CONTENT_TYPE}" \
--data '{ "name": "Heloisa Isabel", "age": 64, "income": 10000.00 }' # Juros Variáveis, Hatch, SUV
echo ""
sleep 0.1

curl --location "${API_URL}" \
--header "${CONTENT_TYPE}" \
--data '{ "name": "Ivan Justo", "age": 18, "income": 5000.00 }' # Juros Fixos, Hatch
echo ""
sleep 0.1

curl --location "${API_URL}" \
--header "${CONTENT_TYPE}" \
--data '{ "name": "Larissa Malta", "age": 25, "income": 12000.00 }' # Juros Fixos, Variáveis, Hatch, SUV
echo ""
sleep 0.1

curl --location "${API_URL}" \
--header "${CONTENT_TYPE}" \
--data '{ "name": "Murilo Naves", "age": 81, "income": 3000.00 }' # Consignado
echo ""
sleep 0.1

echo "Concluída a inserção de todos os clientes."