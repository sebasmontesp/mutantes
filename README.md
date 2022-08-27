# Proyecto Mutantes - Prueba Técnica

**Autor:** Sebastian Montes Peña
**Fecha:** Agosto 27 de 2022

## Ambiente Remoto

**IP Pública BD Postgres: ** 34.69.37.139

**URL conexión:** jdbc:postgresql://34.69.37.139:5432/mutantes_db

**Usuario:** mutantes_user

**Password:** YqihGnJt6rUcWZP

**IP Pública Backend:** http://34.71.108.6:8080

**POST mutant:** http://34.71.108.6:8080/mutant

**Ejemplo Request (body):**

```
{
    "dna": [
     "ATGCTA", "CAGTGC", "TTCTGT", "AGAAGG", "CACCTA", "TCACTG"
    ]
}
```

**GET stats:** http://34.71.108.6:8080/stats

**Ejemplo Response:**

```
{
    "count_mutant_dna": 9,
    "count_human_dna": 3,
    "ratio": 3
}
```

## Ambiente Local

```
mvn install
java -jar ./target/mutantes-0.0.1-SNAPSHOT.jar
```

**GET stats:** http://localhost:8080/stats
