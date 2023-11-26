package bank;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Serializer for {@link Transaction} class
 *
 * @author Kevin Schier
 * @version 1.0
 */
public class TransactionSerializer implements JsonSerializer<Transaction>, JsonDeserializer<Transaction> {

    /**
     * Deserialize transaction from json
     *
     * @param jsonElement                Json to deserialize
     * @param type                       Type of object
     * @param jsonDeserializationContext Context for deserialization
     * @return Correctly deserialized transaction
     * @throws JsonParseException If json is not valid
     */
    @Override
    public Transaction deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject transactionJson = jsonElement.getAsJsonObject();
        String date, description, classname;
        double amount;
        classname = transactionJson.get("CLASSNAME").getAsString();
        JsonObject instance = transactionJson.get("INSTANCE").getAsJsonObject();
        switch (classname) {
            case "Payment" -> {
                date = instance.get("date").getAsString();
                amount = instance.get("amount").getAsDouble();
                description = instance.get("description").getAsString();
                double incomingInterest = instance.get("incomingInterest").getAsDouble();
                double outgoingInterest = instance.get("outgoingInterest").getAsDouble();
                return new Payment(date, amount, description, incomingInterest, outgoingInterest);
            }
            case "OutgoingTransfer" -> {
                date = instance.get("date").getAsString();
                amount = instance.get("amount").getAsDouble();
                description = instance.get("description").getAsString();
                String sender = instance.get("sender").getAsString();
                String recipient = instance.get("recipient").getAsString();
                return new OutgoingTransfer(date, amount, description, sender, recipient);
            }
            case "IncomingTransfer" -> {
                date = instance.get("date").getAsString();
                amount = instance.get("amount").getAsDouble();
                description = instance.get("description").getAsString();
                String sender = instance.get("sender").getAsString();
                String recipient = instance.get("recipient").getAsString();
                return new IncomingTransfer(date, amount, description, sender, recipient);
            }
            default -> throw new JsonParseException("Unknown class: " + classname);
        }
    }

    /**
     * Serialize transaction to json
     *
     * @param transaction              Transaction to serialize
     * @param type                     Type of object
     * @param jsonSerializationContext Context for serialization
     * @return Correctly serialized transaction
     */
    @Override
    public JsonElement serialize(Transaction transaction, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject result = new JsonObject();
        JsonObject attributes = new JsonObject();
        result.addProperty("CLASSNAME", transaction.getClass().getSimpleName());
        if (transaction instanceof Transfer t) {
            attributes.addProperty("sender", t.getSender());
            attributes.addProperty("recipient", t.getRecipient());
        } else if (transaction instanceof Payment p) {
            attributes.addProperty("incomingInterest", p.getIncomingInterest());
            attributes.addProperty("outgoingInterest", p.getOutgoingInterest());
        }
        attributes.addProperty("date", transaction.getDate());
        attributes.addProperty("amount", transaction.getAmount());
        attributes.addProperty("description", transaction.getDescription());
        result.add("INSTANCE", attributes);
        return result;
    }
}
