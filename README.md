# Spring Boot приложение с Grafana, Prometheus, Loki и OpenTelemetry

Этот проект представляет собой Spring Boot приложение, интегрированное с Grafana, Prometheus, Loki и OpenTelemetry для сбора и визуализации метрик, логов и трейсов.

## Описание функционала

1. **Сбор метрик с помощью Prometheus:**
   - Приложение предоставляет метрики через эндпоинт `/actuator/prometheus`.
   - Prometheus собирает эти метрики и сохраняет их для дальнейшего анализа.

2. **Сбор логов с помощью Loki:**
   - Логи приложения отправляются в Loki через драйвер Docker или напрямую через конфигурацию приложения.

3. **Визуализация данных в Grafana:**
   - Grafana подключена к Prometheus и Loki для визуализации метрик и логов.
   - В Grafana настроены дашборды для отображения RPS (Request Per Second) и SPR (Seconds Per Request).

4. **Трейсинг с помощью OpenTelemetry:**
   - OpenTelemetry Collector собирает трейсы и отправляет их в Jaeger или другой бэкенд для анализа.

5. **Развертывание в Docker Compose:**
   - Все сервисы (приложение, Prometheus, Loki, Grafana, OpenTelemetry Collector) развернуты с помощью Docker Compose.

---

## Как протестировать функционал

### 1. Запуск приложения и сервисов

1. Убедитесь, что у вас установлены Docker и Docker Compose.

2. Клонируйте репозиторий:

   ```bash
   git clone <ваш-репозиторий>
   cd <ваш-репозиторий>
   ```

3. Запустите все сервисы с помощью Docker Compose:

   ```bash
   docker-compose up --build
   ```

   После запуска все сервисы будут доступны по следующим адресам:
   - Приложение: `http://localhost:8080`
   - Prometheus: `http://localhost:9090`
   - Grafana: `http://localhost:3000`
   - Loki: `http://localhost:3100`
   - OpenTelemetry Collector: `http://localhost:4317` (OTLP gRPC)

---

### 2. Тестирование метрик

1. Откройте Prometheus (`http://localhost:9090`) и проверьте, что метрики собираются:
   - Введите запрос, например, `http_server_requests_seconds_count`, чтобы увидеть количество HTTP-запросов.

2. Откройте Grafana (`http://localhost:3000`) и проверьте дашборды:
   - Используйте логин `admin` и пароль `admin`.
   - Перейдите в раздел "Dashboards" и откройте дашборд для метрик (например, "Spring Boot Metrics").
   - Убедитесь, что отображаются метрики RPS и SPR.

---

### 3. Тестирование логов

1. Отправьте несколько запросов в приложение, чтобы сгенерировать логи:
   - Например, используйте `curl` для создания пользователя:
     ```bash
     curl -X POST -H "Content-Type: application/json" -d '{"name": "John", "email": "john@example.com"}' http://localhost:8080/api/users
     ```

2. Откройте Grafana и перейдите в раздел "Explore":
   - Выберите источник данных Loki.
   - Введите запрос, например, `{container_name="demo-app"}`, чтобы увидеть логи приложения.

---

### 4. Тестирование трейсинга

1. Убедитесь, что OpenTelemetry Collector настроен для отправки трейсов в Jaeger или другой бэкенд.

2. Отправьте несколько запросов в приложение, чтобы сгенерировать трейсы.

3. Откройте Jaeger (если используется) и проверьте трейсы:
   - Убедитесь, что трейсы корректно отображаются и содержат информацию о запросах.

---

### 5. Тестирование в Kubernetes (опционально)

Если вы развернули приложение в Kubernetes, выполните следующие шаги:

1. Убедитесь, что Prometheus, Loki и Grafana развернуты в кластере.

2. Проверьте, что метрики и логи собираются:
   - Используйте `kubectl port-forward` для доступа к сервисам:
     ```bash
     kubectl port-forward svc/prometheus 9090:9090
     kubectl port-forward svc/grafana 3000:3000
     kubectl port-forward svc/loki 3100:3100
     ```

3. Проверьте дашборды в Grafana и логи в Loki, как описано выше.

---

## Дополнительные баллы

### Использование OpenTelemetry в Kubernetes

1. Убедитесь, что OpenTelemetry Collector развернут в кластере и настроен для сбора трейсов.

2. Проверьте, что трейсы отправляются в Jaeger или другой бэкенд.

3. Убедитесь, что трейсы корректно отображаются в Jaeger.
