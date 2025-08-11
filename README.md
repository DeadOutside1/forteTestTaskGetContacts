# 📞 Contact Manager REST API

Spring Boot приложение для управления контактами с возможностью:
- CRUD операций
- Фильтрации и поиска
- Пагинации и сортировки

## 🚀 Технологии
- **Java 17**
- **Spring Boot 3.5.0**
- **PostgreSQL** / H2 (для разработки)
- **Spring Data JPA**
- **Lombok**

## ⚙️ Запуск проекта

### 1. Требования
- JDK 17+
- PostgreSQL 15+ (или Docker)
- Maven/Gradle

### 2. Настройка БД
```bash
# Создание БД в PostgreSQL
sudo -u postgres psql -c "CREATE DATABASE contactdb;"
sudo -u postgres psql -c "CREATE USER contact_user WITH PASSWORD 'password';"
sudo -u postgres psql -c "GRANT ALL PRIVILEGES ON DATABASE contactdb TO contact_user;"
```
## API endpoints

### 1. Комплексный поиск по всем полям (имя/телефон/email)
- http://localhost:8080/api/contacts/search?query=Kai
### 2. Возвращает первые 5 контактов
- http://localhost:8080/api/contacts?page=0&size=5
### 3. Сортирует по имени в алфавитном порядке
- http://localhost:8080/api/contacts?page=0&size=10&sort=name&direction=asc
### 4. Ищет контакты по имени , Сортирует результаты по ID в обратном порядке
- http://localhost:8080/api/contacts?page=0&size=10&sort=name&direction=asc
### 5.Ищет контакты, где номер телефона содержит подстроку 
- http://localhost:8080/api/contacts?phone=45&size=5
