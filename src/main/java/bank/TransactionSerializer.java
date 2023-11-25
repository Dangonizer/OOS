package bank;

import com.google.gson.*;

import java.lang.reflect.Type;

public class TransactionSerializer implements JsonSerializer<Transaction>, JsonDeserializer<Transaction> {

    @Override
    public Transaction deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return null;
    }

    @Override
    public JsonElement serialize(Transaction transaction, Type type, JsonSerializationContext jsonSerializationContext) {
        return null;
    }
}
