# SmartFit AI - User Service

Mikrostoritev za upravljanje uporabnikov aplikacije SmartFit AI.

## O projektu

SmartFit AI je fitnes aplikacija, ki uporabnikom omogoča sledenje vadbenim aktivnostim in s pomočjo umetne inteligence generira personalizirane vadbene načrte. Ta mikrostoritev skrbi za upravljanje uporabniških računov in profilov.

## Začetek

### Predpogoji
- Java 17 (LTS)
- Maven 3.6+

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
- `GET /v1/users` - Informacije o storitvi

## Povezani repozitoriji

- [Frontend](https://github.com/prpo-smartfit-ai/frontend)
- [Storitev za vadbo](https://github.com/prpo-smartfit-ai/workout-service)
- [AI storitev](https://github.com/prpo-smartfit-ai/ai-service)

## Licenca

MIT