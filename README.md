# yamilyaluk-tp-lp3-2025
Resumen: Es un proyecto hecho con Java Spring Boot que maneja entidades que representan tipos distintos de empleados en una empresa (Por horas, a tiempo completo, gerente o contratistas) y el proyecto se divide principalmente en paquetes: domain (las entidades que modelan a las personas), services (maneja la lógica del negocio), repositories (acceden a la database), controller (manejan los endpoints HTTP), dto (manejan el paso de datos entre capas) y las excepciones
## Instrucciones de Ejecución
1. Clonar el repositorio:
```
   git clone https://github.com/Siegfried177/yamilyaluk-tp-lp3-202
```
2. Entrar al proyecto:
```
   cd yamilyaluk-tp-lp3-2025
```
3. Configurar la base de datos en `src/main/resources/application.properties`

4. Ejecutar la aplicación con Maven:
```
   mvn spring-boot:run
```
5. La API estará disponible en:
   http://localhost:8080/api/personas
   http://localhost:8080/api/nomina

## CURLs con Ejemplos
**1. CREATE**

1.1 Empleado Tiempo Completo
```
curl -X POST "http://localhost:8080/api/nomina" \
  -H "Content-Type: application/json" \
  -d '{
        "tipoEmpleado": "EMPLEADO_TIEMPO_COMPLETO",
        "nombre": "Ana",
        "apellido": "Gomez",
        "numeroDocumento": "1112223",
        "fechaNacimiento": "1995-01-01",
        "fechaContratacion": "2023-05-10",
        "posicionGPS": {"latitud": -25.2000, "longitud": -57.5000},
        "salarioMensual": 5000000.00,
        "departamento": "FINANZAS"
      }'
```
1.2 Empleado por Hora
```
curl -X POST "http://localhost:8080/api/nomina" \
  -H "Content-Type: application/json" \
  -d '{
        "tipoEmpleado": "EMPLEADO_POR_HORA",
        "nombre": "Marta",
        "apellido": "Vera",
        "numeroDocumento": "7778889",
        "fechaNacimiento": "2000-10-10",
        "fechaContratacion": "2024-05-01",
        "posicionGPS": {"latitud": -25.1000, "longitud": -57.4000},
        "tarifaPorHora": 50000.00,
        "horasTrabajadas": 45
      }'
```
1.3 Contratista
```
curl -X POST "http://localhost:8080/api/nomina" \
  -H "Content-Type: application/json" \
  -d '{
        "tipoEmpleado": "CONTRATISTA",
        "nombre": "Pedro",
        "apellido": "Rojas",
        "numeroDocumento": "4445556",
        "fechaNacimiento": "1988-03-01",
        "fechaContratacion": "2024-01-01",
        "posicionGPS": {"latitud": -25.4000, "longitud": -57.6500},
        "montoPorProyecto": 2500000.00,
        "proyectosCompletados": 2,
        "fechaFinContrato": "2026-06-30"
      }'
```
1.4 Gerente
```
curl -X POST "http://localhost:8080/api/nomina" \
  -H "Content-Type: application/json" \
  -d '{
        "tipoEmpleado": "GERENTE",
        "nombre": "Javier",
        "apellido": "Rios",
        "numeroDocumento": "8877665",
        "fechaNacimiento": "1985-06-20",
        "fechaContratacion": "2020-01-01",
        "areaResponsabilidad": "Marketing",
        "añosAntiguedad": 444,
        "posicionGPS": {"latitud": -25.3000, "longitud": -57.6000}
      }'
```
2. **Solicitar Vacaciones**

2.1 Correcto
```
curl -X POST "http://localhost:8080/api/nomina/solicitarVacaciones" \
  -H "Content-Type: application/json" \
  -d '{
        "empleadoId": 66,
        "fechaInicio": "2022-03-01",
        "fechaFin": "2022-03-14"
      }'
```  
2.2 Excepcion por Pedir más dias que el máximo (25 para Gerentes, 20 para los demás)
```
curl -X POST "http://localhost:8080/api/nomina/solicitarVacaciones" \
  -H "Content-Type: application/json" \
  -d '{
        "empleadoId": 66,
        "fechaInicio": "2022-03-01",
        "fechaFin": "2022-03-30"
      }'
```   
2.3 Excepcion por Fecha Incorrecta
```
curl -X POST "http://localhost:8080/api/nomina/solicitarVacaciones" \
  -H "Content-Type: application/json" \
  -d '{
        "empleadoId": 66,
        "fechaInicio": "2032-03-01",
        "fechaFin": "2022-03-30"
      }'
```
**3. Listar Todos los Empleados**
```
curl -X GET "http://localhost:8080/api/nomina/completa" \
  -H "Accept: application/json"
```
**4. GET por ID**
```
curl -X GET "http://localhost:8080/api/nomina/65" \
  -H "Accept: application/json"
```
**5. UPDATE por ID**
```
curl -X PUT "http://localhost:8080/api/nomina/57" \
  -H "Content-Type: application/json" \
  -d '{
        "tipoEmpleado": "GERENTE",
        "nombre": "Javisssser",
        "apellido": "Rios",
        "numeroDocumento": "8877665",
        "fechaNacimiento": "1985-06-20",
        "fechaContratacion": "2020-01-01",
        "areaResponsabilidad": "MARKETING Y PUBLICIDAD",
        "añosAntiguedad": 5,
        "posicionGPS": {"latitud": -25.4500, "longitud": -57.7000}
      }'
```
**6. DELETE por ID**
```
curl -X DELETE "http://localhost:8080/api/nomina/55" \
  -H "Accept: application/json"
```
**7. Reporte Polimorfismo**
```
curl -X GET http://localhost:8080/api/personas/reporte/polimorfismo
  -H "Accept: application/json"
```
**8. GET por Nombre**
```
curl -X GET "http://localhost:8080/api/personas?nombre=Javier" \
  -H "Accept: application/json"
```
**9. GET Nomina Total**
```
curl -X GET "http://localhost:8080/api/personas/nomina" \
  -H "Accept: application/json"
```
