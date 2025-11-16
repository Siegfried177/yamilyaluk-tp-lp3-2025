# yamilyaluk-tp-lp3-2025

## Instrucciones de Ejecución
1. Clonar el repositorio:
   git clone https://github.com/Siegfried177/yamilyaluk-tp-lp3-202

2. Entrar al proyecto:
   cd yamilyaluk-tp-lp3-2025

3. Configurar la base de datos en `src/main/resources/application.properties`

4. Ejecutar la aplicación con Maven:
   mvn spring-boot:run

5. La API estará disponible en:
   http://localhost:8080/api/personas

## CURLs con Ejemplos
1. Batch de varios Empleados Tiempo Completo (Son validados) --  

   curl -X POST "http://localhost:8080/api/empleados-tiempo-completo/batch" \
  -H "Content-Type: application/json" \
  -d '{
        "empleados": [
          {
            "nombre": "Carlos Ruiz",
            "salarioBase": 5500000,
            "departamento": "IT",
            "fechaNacimiento": "1990-05-12"
          },
          {
            "nombre": "Ana Lopez",
            "salarioBase": 4300000,
            "departamento": "Ventas",
            "fechaNacimiento": "1988-10-21"
          }
        ]
      }'
   
2. Listar todos los empleados --

curl -X GET "http://localhost:8080/api/personas/listar-todos" \
  -H "Accept: application/json"
  
3. Buscar empleado por ID --

curl -X GET "http://localhost:8080/api/personas/3" \
  -H "Accept: application/json"

4. Buscar empleado por Nombre --

curl -X GET "http://localhost:8080/api/personas?nombre=Carlos" \
  -H "Accept: application/json"

5. Consulta de nomina -- 

curl -X GET "http://localhost:8080/api/personas/nomina" \
  -H "Accept: application/json"

6. Reporte de Polimorfismo --

curl -X GET "http://localhost:8080/api/personas/reporte-polimorfismo" \
  -H "Accept: application/json"



## Screenshots
![Batch exitoso](screenshots/batch.png)
![Batch Incorrecto](screenshots/batch_salario_incorrecto.png)
![Buscar por ID](screenshots/buscar_porID.png)
![Buscar por Nombre](screenshots/buscar_porNombre.png)
![Listar Todos](screenshots/listar_todos.png)
![Nomina](screenshots/nomina.png)
![Reporte de Polimorfismo](screenshots/reporte.png)
