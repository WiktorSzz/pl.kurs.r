package pl.kurs.models;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;

public enum ObjectMapperHolder {
    INSTANCE;

    private final ObjectMapper objectMapper;

    ObjectMapperHolder() {
        objectMapper = create();
    }

    public ObjectMapper getObjectMapper(){
        return objectMapper;
    }

    private static ObjectMapper create() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setDefaultTyping(new ObjectMapper.DefaultTypeResolverBuilder(ObjectMapper.DefaultTyping.NON_FINAL,
                BasicPolymorphicTypeValidator.builder().build()) {
            {
                init(JsonTypeInfo.Id.NAME, null);
                inclusion(JsonTypeInfo.As.PROPERTY);
                typeProperty("type");
            }

            @Override
            public boolean useForType(JavaType t) {
                return !t.isContainerType() && super.useForType(t);
            }
        });

        return objectMapper;
    }
}