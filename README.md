# SmartFit AI - User Service

Mikrostoritev za upravljanje uporabnikov aplikacije SmartFit AI.

## O projektu

SmartFit AI je fitnes aplikacija, ki uporabnikom omogoča sledenje vadbenim aktivnostim in s pomočjo umetne inteligence generira personalizirane vadbene načrte. Ta mikrostoritev skrbi za upravljanje uporabniških računov in profilov.

## Začetek

### Predpogoji
- Java 17 (LTS)
- Maven 3.6+
- PostgreSQL 15+

### Nastavitev baze podatkov

```bash
psql postgres
CREATE DATABASE smartfitai_users;
CREATE USER smartfit WITH PASSWORD 'smartfit123';
GRANT ALL PRIVILEGES ON DATABASE smartfitai_users TO smartfit;
\q
```

Tabele se ustvarijo avtomatsko ob prvem zagonu.

### Gradnja

```bash
mvn clean package
```

### Zagon

```bash
java -jar target/user-service-1.0.0-SNAPSHOT.jar
```

Storitev bo dostopna na `http://localhost:8080`

## API

- `GET /v1/users/health` - Preverjanje stanja storitve
- `POST /v1/users/register` - Registracija novega uporabnika
- `POST /v1/users/login` - Prijava uporabnika
- `GET /v1/users/profile` - Pridobitev profila uporabnika (zahteva JWT)
- `PUT /v1/users/profile` - Posodobitev profila uporabnika (zahteva JWT)

## Povezani repozitoriji

- [Frontend](https://github.com/prpo-smartfit-ai/frontend)
- [Storitev za vadbo](https://github.com/prpo-smartfit-ai/workout-service)
- [AI storitev](https://github.com/prpo-smartfit-ai/ai-service)

## Licenca

MIT