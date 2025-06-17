# Hướng dẫn sử dụng FilterSpecification

## Tổng quan

`FilterSpecification` là một utility class được thiết kế để tạo các điều kiện lọc (filter) động cho Spring Data JPA Specification. Nó cho phép bạn xây dựng các query phức tạp một cách dễ dàng và linh hoạt, bao gồm cả các phép toán aggregate như AVG, COUNT, SUM, MAX, MIN.

## Cấu trúc hệ thống Filter

### 1. SearchCriteria
Đây là class chứa thông tin về một điều kiện tìm kiếm:
```java
public class SearchCriteria {
    private String fieldName;      // Tên trường cần filter
    private Operation operation;   // Phép toán (EQUALS, CONTAINS, AVG_EQUALS, etc.)
    private Object comparedValue;  // Giá trị để so sánh
}
```

### 2. Operation Enum

Định nghĩa các phép toán có thể sử dụng:

#### Basic Operations (Phép toán cơ bản)
- **EQUALS**: Bằng
- **NOT_EQUALS**: Khác
- **GREATER_THAN**: Lớn hơn
- **LESS_THAN**: Nhỏ hơn
- **GREATER_THAN_OR_EQUAL_TO**: Lớn hơn hoặc bằng
- **LESS_THAN_OR_EQUAL_TO**: Nhỏ hơn hoặc bằng
- **LIKE**: Giống (pattern matching)
- **NOT_LIKE**: Không giống
- **CONTAINS**: Chứa (tự động thêm % ở đầu và cuối)
- **NOT_CONTAINS**: Không chứa
- **IN**: Có trong danh sách
- **NOT_IN**: Không có trong danh sách
- **IS_NULL**: Là null
- **IS_NOT_NULL**: Không null
- **BETWEEN**: Trong khoảng
- **NOT_BETWEEN**: Không trong khoảng

#### Aggregate Operations (Phép toán tập hợp)

**Average Operations:**
- **AVG_EQUALS**: Trung bình bằng
- **AVG_NOT_EQUALS**: Trung bình khác
- **AVG_GREATER_THAN**: Trung bình lớn hơn
- **AVG_LESS_THAN**: Trung bình nhỏ hơn
- **AVG_GREATER_THAN_OR_EQUAL_TO**: Trung bình lớn hơn hoặc bằng
- **AVG_LESS_THAN_OR_EQUAL_TO**: Trung bình nhỏ hơn hoặc bằng

**Count Operations:**
- **COUNT_EQUALS**: Số lượng bằng
- **COUNT_NOT_EQUALS**: Số lượng khác
- **COUNT_GREATER_THAN**: Số lượng lớn hơn
- **COUNT_LESS_THAN**: Số lượng nhỏ hơn
- **COUNT_GREATER_THAN_OR_EQUAL_TO**: Số lượng lớn hơn hoặc bằng
- **COUNT_LESS_THAN_OR_EQUAL_TO**: Số lượng nhỏ hơn hoặc bằng

**Sum Operations:**
- **SUM_EQUALS**: Tổng bằng
- **SUM_NOT_EQUALS**: Tổng khác
- **SUM_GREATER_THAN**: Tổng lớn hơn
- **SUM_LESS_THAN**: Tổng nhỏ hơn
- **SUM_GREATER_THAN_OR_EQUAL_TO**: Tổng lớn hơn hoặc bằng
- **SUM_LESS_THAN_OR_EQUAL_TO**: Tổng nhỏ hơn hoặc bằng

**Max/Min Operations:**
- **MAX_EQUALS**: Giá trị lớn nhất bằng
- **MAX_NOT_EQUALS**: Giá trị lớn nhất khác
- **MAX_GREATER_THAN**: Giá trị lớn nhất lớn hơn
- **MAX_LESS_THAN**: Giá trị lớn nhất nhỏ hơn
- **MAX_GREATER_THAN_OR_EQUAL_TO**: Giá trị lớn nhất lớn hơn hoặc bằng
- **MAX_LESS_THAN_OR_EQUAL_TO**: Giá trị lớn nhất nhỏ hơn hoặc bằng
- **MIN_EQUALS**: Giá trị nhỏ nhất bằng
- **MIN_NOT_EQUALS**: Giá trị nhỏ nhất khác
- **MIN_GREATER_THAN**: Giá trị nhỏ nhất lớn hơn
- **MIN_LESS_THAN**: Giá trị nhỏ nhất nhỏ hơn
- **MIN_GREATER_THAN_OR_EQUAL_TO**: Giá trị nhỏ nhất lớn hơn hoặc bằng
- **MIN_LESS_THAN_OR_EQUAL_TO**: Giá trị nhỏ nhất nhỏ hơn hoặc bằng

#### Logical Operations (Phép toán logic)
- **AND**: Phép AND (dùng để kết hợp điều kiện)
- **OR**: Phép OR (dùng để kết hợp điều kiện)

### 3. FilterSpecification
Class chính để xây dựng các điều kiện filter. Đã được cải tiến với null-safety tốt hơn.

### 4. GenericSpecification
Class implement Specification với khả năng xử lý:
- Nested fields (truy cập trường lồng nhau)
- Aggregate functions (AVG, COUNT, SUM, MAX, MIN)
- Type conversion an toàn cho số
- GROUP BY và HAVING clauses

## Cách sử dụng

### 1. Tạo Repository extend JpaSpecificationExecutor

**Quan trọng**: Repository của bạn phải extend `JpaSpecificationExecutor` để có thể sử dụng Specification:

```java
@Repository
public interface StaffRepository extends JpaRepository<StaffEntity, Long>, JpaSpecificationExecutor<StaffEntity> {
    // Các custom method khác (nếu có)
}
```

**Ví dụ các Repository khác:**

```java
@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, Long>, JpaSpecificationExecutor<PatientEntity> {
}

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long>, JpaSpecificationExecutor<AppointmentEntity> {
}

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long>, JpaSpecificationExecutor<DepartmentEntity> {
}
```

**Lưu ý**: 
- `JpaSpecificationExecutor<T>` cung cấp các method như `findAll(Specification<T> spec)`, `findOne(Specification<T> spec)`, `count(Specification<T> spec)`
- Nếu không extend interface này, bạn sẽ không thể sử dụng FilterSpecification

### 2. Inject FilterSpecification vào Controller/Service

```java
@RestController
public class YourController {
    
    @Autowired
    private FilterSpecification<YourEntity> filterSpecification;
    
    @Autowired
    private YourRepository yourRepository; // Repository phải extend JpaSpecificationExecutor
}
```

### 3. Xây dựng điều kiện filter

#### Cách 1: Sử dụng addSearchCriteria (Recommended)
```java
// Tạo một điều kiện filter mới
filterSpecification = filterSpecification
    .addSearchCriteria(new SearchCriteria("fieldName", Operation.EQUALS, value), Operation.AND);
```

#### Cách 2: Sử dụng getSpecification
```java
// Tạo specification từ SearchCriteria
Specification<YourEntity> spec = filterSpecification
    .getSpecification(new SearchCriteria("fieldName", Operation.EQUALS, value));

// Hoặc sử dụng trực tiếp
Specification<YourEntity> spec = filterSpecification
    .getSpecification("fieldName", Operation.EQUALS, value);
```

#### Cách 3: Sử dụng getSpecifications để kết hợp nhiều điều kiện
```java
// Kết hợp nhiều điều kiện với AND
List<SearchCriteria> criterias = Arrays.asList(
    new SearchCriteria("name", Operation.CONTAINS, "John"),
    new SearchCriteria("age", Operation.GREATER_THAN, 25)
);
Specification<YourEntity> spec = filterSpecification.getSpecifications(criterias);
```

### 4. Sử dụng với Repository

```java
// Áp dụng filter vào query
List<YourEntity> results = yourRepository.findAll(filterSpecification.getSpecification());
```

## Ví dụ thực tế: DoctorAPI (Đã cập nhật)

Dưới đây là ví dụ mới nhất từ `DoctorAPI` với các phép toán aggregate:

```java
@GetMapping
public List<StaffResponse> getDoctors(@RequestBody StaffDTO staffDTO) {
    // Lọc chỉ những staff có role là DOCTOR
    filterSpecification = filterSpecification
        .addSearchCriteria(new SearchCriteria("staffRole", Operation.EQUALS, DOCTOR_ROLE), Operation.AND);
    
    // Lọc theo tên (tìm kiếm gần đúng)
    filterSpecification = filterSpecification
        .addSearchCriteria(new SearchCriteria("fullName", Operation.CONTAINS, staffDTO.getFullName()), Operation.AND);
    
    // Lọc theo tên department (truy cập nested field)
    filterSpecification = filterSpecification
        .addSearchCriteria(new SearchCriteria("departmentEntity.name", Operation.EQUALS, staffDTO.getDepartmentEntityName()), Operation.AND);
    
    // Lọc theo tên hospital (truy cập nested field)
    filterSpecification = filterSpecification
        .addSearchCriteria(new SearchCriteria("hospitalEntity.name", Operation.EQUALS, staffDTO.getHospitalEntityName()), Operation.AND);
    
    // MỚI: Lọc theo rating trung bình (sử dụng AVG aggregate)
    filterSpecification = filterSpecification
        .addSearchCriteria(new SearchCriteria("reviewEntities.rating", Operation.AVG_EQUALS, staffDTO.getAverageRating()), Operation.AND);
    
    // MỚI: Lọc theo số lượng review tối thiểu (sử dụng COUNT aggregate)
    filterSpecification = filterSpecification
        .addSearchCriteria(new SearchCriteria("reviewEntities.id", Operation.COUNT_GREATER_THAN_OR_EQUAL_TO, staffDTO.getReviewCount()), Operation.AND);
    
    // Thực thi query và chuyển đổi kết quả
    return staffRepository.findAll(filterSpecification.getSpecification())
        .stream()
        .map(staffConverter::toResponse)
        .toList();
}
```

## Các tính năng nâng cao

### 1. Truy cập Nested Fields
Bạn có thể truy cập các trường lồng nhau bằng cách sử dụng dấu chấm (.):
```java
// Truy cập trường name của departmentEntity
new SearchCriteria("departmentEntity.name", Operation.EQUALS, "Cardiology")

// Truy cập trường nested sâu hơn
new SearchCriteria("user.profile.address.city", Operation.EQUALS, "Ho Chi Minh")

// Truy cập trường trong collection (cho aggregate functions)
new SearchCriteria("reviewEntities.rating", Operation.AVG_GREATER_THAN, 4.0)
```

### 2. Sử dụng Aggregate Functions

#### Average (Trung bình)
```java
// Tìm bác sĩ có rating trung bình lớn hơn 4.5
filterSpecification = filterSpecification
    .addSearchCriteria(new SearchCriteria("reviewEntities.rating", Operation.AVG_GREATER_THAN, 4.5), Operation.AND);

// Tìm sản phẩm có giá trung bình bằng 100.000
filterSpecification = filterSpecification
    .addSearchCriteria(new SearchCriteria("orderItems.price", Operation.AVG_EQUALS, 100000.0), Operation.AND);
```

#### Count (Đếm)
```java
// Tìm bác sĩ có ít nhất 10 review
filterSpecification = filterSpecification
    .addSearchCriteria(new SearchCriteria("reviewEntities.id", Operation.COUNT_GREATER_THAN_OR_EQUAL_TO, 10), Operation.AND);

// Tìm khách hàng có đúng 5 đơn hàng
filterSpecification = filterSpecification
    .addSearchCriteria(new SearchCriteria("orders.id", Operation.COUNT_EQUALS, 5), Operation.AND);
```

#### Sum (Tổng)
```java
// Tìm khách hàng có tổng giá trị đơn hàng lớn hơn 1 triệu
filterSpecification = filterSpecification
    .addSearchCriteria(new SearchCriteria("orders.totalAmount", Operation.SUM_GREATER_THAN, 1000000.0), Operation.AND);
```

#### Max/Min (Giá trị lớn nhất/nhỏ nhất)
```java
// Tìm sản phẩm có giá cao nhất lớn hơn 500.000
filterSpecification = filterSpecification
    .addSearchCriteria(new SearchCriteria("variants.price", Operation.MAX_GREATER_THAN, 500000.0), Operation.AND);

// Tìm sản phẩm có giá thấp nhất nhỏ hơn 50.000
filterSpecification = filterSpecification
    .addSearchCriteria(new SearchCriteria("variants.price", Operation.MIN_LESS_THAN, 50000.0), Operation.AND);
```

### 3. Sử dụng phép OR
```java
filterSpecification = filterSpecification
    .addSearchCriteria(new SearchCriteria("status", Operation.EQUALS, "ACTIVE"), Operation.OR)
    .addSearchCriteria(new SearchCriteria("status", Operation.EQUALS, "PENDING"), Operation.OR);
```

### 4. Sử dụng phép BETWEEN
```java
// Lọc theo khoảng tuổi
Object[] ageRange = {25, 65};
filterSpecification = filterSpecification
    .addSearchCriteria(new SearchCriteria("age", Operation.BETWEEN, ageRange), Operation.AND);
```

### 5. Sử dụng phép IN
```java
// Lọc theo danh sách ID
List<Long> ids = Arrays.asList(1L, 2L, 3L);
filterSpecification = filterSpecification
    .addSearchCriteria(new SearchCriteria("id", Operation.IN, ids), Operation.AND);
```

### 6. Kiểm tra NULL
```java
// Lọc những record có email không null
filterSpecification = filterSpecification
    .addSearchCriteria(new SearchCriteria("email", Operation.IS_NOT_NULL, null), Operation.AND);
```

## Ví dụ hoàn chỉnh khác

### Ví dụ 1: Patient API với Aggregate Functions
```java
@GetMapping("/patients")
public List<PatientResponse> getPatients(
    @RequestParam(required = false) String name,
    @RequestParam(required = false) Integer minAge,
    @RequestParam(required = false) Integer maxAge,
    @RequestParam(required = false) String department,
    @RequestParam(required = false) Double minAvgRating,
    @RequestParam(required = false) Integer minAppointmentCount) {
    
    FilterSpecification<PatientEntity> filter = new FilterSpecification<>();
    
    // Tìm kiếm theo tên (nếu có)
    if (name != null && !name.isEmpty()) {
        filter = filter.addSearchCriteria(
            new SearchCriteria("fullName", Operation.CONTAINS, name), 
            Operation.AND);
    }
    
    // Lọc theo độ tuổi
    if (minAge != null) {
        filter = filter.addSearchCriteria(
            new SearchCriteria("age", Operation.GREATER_THAN_OR_EQUAL_TO, minAge), 
            Operation.AND);
    }
    
    if (maxAge != null) {
        filter = filter.addSearchCriteria(
            new SearchCriteria("age", Operation.LESS_THAN_OR_EQUAL_TO, maxAge), 
            Operation.AND);
    }
    
    // Lọc theo department
    if (department != null && !department.isEmpty()) {
        filter = filter.addSearchCriteria(
            new SearchCriteria("department.name", Operation.EQUALS, department), 
            Operation.AND);
    }
    
    // Lọc theo rating trung bình tối thiểu
    if (minAvgRating != null) {
        filter = filter.addSearchCriteria(
            new SearchCriteria("reviews.rating", Operation.AVG_GREATER_THAN_OR_EQUAL_TO, minAvgRating), 
            Operation.AND);
    }
    
    // Lọc theo số lượng appointment tối thiểu
    if (minAppointmentCount != null) {
        filter = filter.addSearchCriteria(
            new SearchCriteria("appointments.id", Operation.COUNT_GREATER_THAN_OR_EQUAL_TO, minAppointmentCount), 
            Operation.AND);
    }
    
    return patientRepository.findAll(filter.getSpecification())
        .stream()
        .map(patientConverter::toResponse)
        .toList();
}
```

### Ví dụ 2: Product API với Multiple Aggregates
```java
@GetMapping("/products")
public List<ProductResponse> getProducts(
    @RequestParam(required = false) String category,
    @RequestParam(required = false) Double maxAvgPrice,
    @RequestParam(required = false) Integer minOrderCount,
    @RequestParam(required = false) Double minTotalRevenue) {
    
    FilterSpecification<ProductEntity> filter = new FilterSpecification<>();
    
    // Lọc theo category
    if (category != null) {
        filter = filter.addSearchCriteria(
            new SearchCriteria("category.name", Operation.EQUALS, category), 
            Operation.AND);
    }
    
    // Lọc sản phẩm có giá trung bình không quá cao
    if (maxAvgPrice != null) {
        filter = filter.addSearchCriteria(
            new SearchCriteria("variants.price", Operation.AVG_LESS_THAN_OR_EQUAL_TO, maxAvgPrice), 
            Operation.AND);
    }
    
    // Lọc sản phẩm có ít nhất một số lượng order
    if (minOrderCount != null) {
        filter = filter.addSearchCriteria(
            new SearchCriteria("orderItems.id", Operation.COUNT_GREATER_THAN_OR_EQUAL_TO, minOrderCount), 
            Operation.AND);
    }
    
    // Lọc sản phẩm có tổng doanh thu tối thiểu
    if (minTotalRevenue != null) {
        filter = filter.addSearchCriteria(
            new SearchCriteria("orderItems.totalPrice", Operation.SUM_GREATER_THAN_OR_EQUAL_TO, minTotalRevenue), 
            Operation.AND);
    }
    
    return productRepository.findAll(filter.getSpecification())
        .stream()
        .map(productConverter::toResponse)
        .toList();
}
```

## Cải tiến mới trong FilterSpecification

### 1. Null Safety được cải thiện
```java
// Trong FilterSpecification.getSpecification()
if (searchCriteria == null || searchCriteria.getFieldName() == null || 
    searchCriteria.getOperation() == null || searchCriteria.getComparedValue() == null) {
    return null; // Trả về null thay vì Specification.allOf()
}

// Trong getSpecifications(), skip invalid criteria
if (newSpecification == null) {
    continue; // Skip invalid search criteria
}
```

### 2. Type Conversion an toàn
```java
// GenericSpecification có method convertNumberValue để convert an toàn
private <T extends Number> T convertNumberValue(Object value, Class<T> targetType) {
    if (value == null) return null;
    
    Number numValue = (Number) value;
    if (targetType == Double.class) {
        return (T) Double.valueOf(numValue.doubleValue());
    } else if (targetType == Long.class) {
        return (T) Long.valueOf(numValue.longValue());
    }
    // ... các kiểu khác
    
    return (T) numValue;
}
```

## Lưu ý quan trọng

### 1. Null Safety
- Hệ thống đã xử lý null safety tốt hơn
- Invalid SearchCriteria sẽ được skip thay vì gây lỗi
- Luôn kiểm tra null cho các field quan trọng

### 2. Aggregate Functions
- **Performance**: Aggregate functions sử dụng GROUP BY và HAVING, có thể chậm với dataset lớn
- **Index**: Đảm bảo database có index phù hợp cho các trường được aggregate
- **Field Type**: Chỉ sử dụng aggregate functions với numeric fields

### 3. Field Name
- Đảm bảo tên field chính xác và tồn tại trong Entity
- Với nested fields, kiểm tra các relationship đã được map đúng
- Với collection fields (dùng cho aggregate), đảm bảo relationship type chính xác

### 4. Data Type
- Đảm bảo kiểu dữ liệu của `comparedValue` phù hợp với field trong Entity
- Với aggregate functions, thường sử dụng Number types (Double, Long, Integer)

### 5. Dependency Injection
- `FilterSpecification` được đánh dấu `@Component`, có thể inject trực tiếp
- Mỗi request nên tạo FilterSpecification mới để tránh state conflict

## Troubleshooting

### Lỗi thường gặp:

1. **Field không tồn tại**: Kiểm tra tên field trong Entity
2. **Kiểu dữ liệu không khớp**: Đảm bảo `comparedValue` có kiểu dữ liệu đúng
3. **Nested field không được tìm thấy**: Kiểm tra relationship và tên field nested
4. **Aggregate function lỗi**: Kiểm tra field có phải numeric type không
5. **Performance issue**: Với aggregate functions, cần optimize query và index

### Debug tips:
- Sử dụng logging để xem SQL query được generate
- Test từng điều kiện một cách riêng biệt
- Kiểm tra Entity mapping và relationships
- Với aggregate functions, kiểm tra GROUP BY clause có đúng không
- Monitor performance với large datasets

### SQL Generated Examples:
```sql
-- Basic query
SELECT * FROM staff WHERE staff_role = 'DOCTOR' AND full_name LIKE '%John%'

-- With aggregate (AVG)
SELECT * FROM staff s 
JOIN review r ON s.id = r.staff_id 
GROUP BY s.id 
HAVING AVG(r.rating) >= 4.5

-- With multiple aggregates
SELECT * FROM staff s 
JOIN review r ON s.id = r.staff_id 
GROUP BY s.id 
HAVING AVG(r.rating) >= 4.5 AND COUNT(r.id) >= 10
```

## Best Practices

1. **Sử dụng Index**: Với aggregate functions, tạo index cho các trường liên quan
2. **Limit Results**: Với aggregate queries, luôn sử dụng pagination
3. **Validate Input**: Kiểm tra input parameters trước khi tạo SearchCriteria
4. **Error Handling**: Wrap filter logic trong try-catch để handle exceptions
5. **Performance Monitoring**: Monitor query performance, đặc biệt với aggregate functions

## Kết luận

`FilterSpecification` đã được nâng cấp với khả năng hỗ trợ aggregate functions mạnh mẽ, cho phép tạo các query phức tạp như tính toán trung bình, đếm, tổng, min/max trên các collection relationships. Hệ thống cung cấp một cách linh hoạt và type-safe để xây dựng dynamic queries trong Spring Data JPA, đặc biệt hữu ích cho các API cần hỗ trợ nhiều tiêu chí tìm kiếm và thống kê phức tạp.
