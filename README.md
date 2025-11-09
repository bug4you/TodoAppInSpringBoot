## API Endpointlar (test qilish uchun)

| Method   | URL                    | Maqsad                |
|----------|------------------------|-----------------------|
| `GET`    | `/api/tasks`           | Barcha vazifalar      |
| `GET`    | `/api/tasks/active`    | Faol vazifalar        |
| `GET`    | `/api/tasks/completed` | Tugallangan vazifalar |
| `POST`   | `/api/tasks`           | Yangi vazifa qo‘shish |
| `PATCH`  | `/api/tasks/1/toggle`  | Holatni o‘zgartirish  |
| `DELETE` | `/api/tasks/1`         | Vazifani o‘chirish    |