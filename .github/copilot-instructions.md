## Coding style

### conditionals + early returns

- Always use braces for `if` blocks (even for a single statement).
- When returning early, put `return` on its own line inside the block.
- Do **not** use single-line `if` without braces.

**Preferred**
```java
if (cond) {
  return;
}
```

**Not allowed**
```java
if (cond) return;
```

## Lombok style:

- Use Lombok’s annotation (correct spelling/casing and import).

**Preferred**
```java
import lombok.Getter;

@Getter
private String name;
```

**Not allowed**
```java
@lombook.getter
private String name;
```

## Domain layer rule (no Lombok)

- Inside the **domain layer**, do **not** use Lombok annotations (including `@Getter`, `@Setter`, `@Data`, etc.).
- Use explicit Java code (constructors, getters) in domain models to keep the domain independent of Lombok and reduce hidden/generated behavior.