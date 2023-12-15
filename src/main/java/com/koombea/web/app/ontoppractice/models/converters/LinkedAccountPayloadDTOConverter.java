/*
 * @creator: Oswaldo Montes
 * @date: December 14, 2023
 *
 */
package com.koombea.web.app.ontoppractice.models.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.koombea.web.app.ontoppractice.dtos.merge.LinkedAccountPayloadDTO;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.io.IOException;

@Converter(autoApply = true)
public class LinkedAccountPayloadDTOConverter implements AttributeConverter<LinkedAccountPayloadDTO, String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(LinkedAccountPayloadDTO attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting JSON to string", e);
        }
    }

    @Override
    public LinkedAccountPayloadDTO convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, LinkedAccountPayloadDTO.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting string to JSON", e);
        }
    }
}

