/*
 * Copyright (c) 2020. MobilityData IO.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mobilitydata.gtfsvalidator.exporter;

import com.fasterxml.jackson.core.JsonGenerator;
import org.junit.jupiter.api.Test;
import org.mobilitydata.gtfsvalidator.domain.entity.notice.error.*;
import org.mobilitydata.gtfsvalidator.domain.entity.notice.warning.*;
import org.mockito.ArgumentMatchers;
import org.mockito.InOrder;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;

import static org.mockito.Mockito.*;

class JsonNoticeExporterTest {
    private static final String FILENAME = "test.tst";

    @Test
    void exportBeginWriteStartObjectAndResultArray() throws IOException {
        JsonGenerator mockGenerator = mock(JsonGenerator.class);

        JsonNoticeExporter underTest = new JsonNoticeExporter(mockGenerator);

        underTest.exportBegin();

        InOrder inOrder = inOrder(mockGenerator);
        inOrder.verify(mockGenerator, times(1)).writeStartObject();
        inOrder.verify(mockGenerator, times(1)).writeFieldName(
                ArgumentMatchers.eq("results"));
        inOrder.verify(mockGenerator, times(1)).writeStartArray();
    }

    @Test
    void exportEndWriteEndArrayEndObjectAndFlush() throws IOException {
        JsonGenerator mockGenerator = mock(JsonGenerator.class);

        JsonNoticeExporter underTest = new JsonNoticeExporter(mockGenerator);

        underTest.exportEnd();

        InOrder inOrder = inOrder(mockGenerator);
        inOrder.verify(mockGenerator, times(1)).writeEndArray();
        inOrder.verify(mockGenerator, times(1)).writeEndObject();
        inOrder.verify(mockGenerator, times(1)).flush();
    }


    @Test
    void exportNonStandardHeaderNoticeShouldWriteObject() throws IOException {
        JsonGenerator mockGenerator = mock(JsonGenerator.class);

        JsonNoticeExporter underTest = new JsonNoticeExporter(mockGenerator);
        NonStandardHeaderNotice toExport = new NonStandardHeaderNotice(FILENAME, "extra");
        underTest.export(toExport);

        verify(mockGenerator, times(1)).writeObject(ArgumentMatchers.eq(toExport));
        verifyNoMoreInteractions(mockGenerator);
    }

    @Test
    void exportInputZipContainsFolderNoticeShouldWriteObject() throws IOException {
        JsonGenerator mockGenerator = mock(JsonGenerator.class);

        JsonNoticeExporter underTest = new JsonNoticeExporter(mockGenerator);
        InputZipContainsFolderNotice toExport = new InputZipContainsFolderNotice(FILENAME, "extraFolder");
        underTest.export(toExport);

        verify(mockGenerator, times(1)).writeObject(ArgumentMatchers.eq(toExport));
        verifyNoMoreInteractions(mockGenerator);
    }

    @Test
    void exportNonAsciiOrNonPrintableCharNoticeShouldWriteObject() throws IOException {
        JsonGenerator mockGenerator = mock(JsonGenerator.class);

        JsonNoticeExporter underTest = new JsonNoticeExporter(mockGenerator);
        NonAsciiOrNonPrintableCharNotice toExport = new NonAsciiOrNonPrintableCharNotice(
                FILENAME,
                "field_name",
                "entity_id",
                "entity_id_value"
        );
        underTest.export(toExport);

        verify(mockGenerator, times(1)).writeObject(ArgumentMatchers.eq(toExport));
        verifyNoMoreInteractions(mockGenerator);
    }

    @Test
    void exportCannotConstructDataProviderNoticeShouldWriteObject() throws IOException {
        JsonGenerator mockGenerator = mock(JsonGenerator.class);

        JsonNoticeExporter underTest = new JsonNoticeExporter(mockGenerator);
        CannotConstructDataProviderNotice toExport = new CannotConstructDataProviderNotice(FILENAME);
        underTest.export(toExport);

        verify(mockGenerator, times(1)).writeObject(ArgumentMatchers.eq(toExport));
        verifyNoMoreInteractions(mockGenerator);
    }

    @Test
    void exportCannotDownloadArchiveFromNetworkNoticeShouldWriteObject() throws IOException {
        JsonGenerator mockGenerator = mock(JsonGenerator.class);

        JsonNoticeExporter underTest = new JsonNoticeExporter(mockGenerator);
        CannotDownloadArchiveFromNetworkNotice toExport = new CannotDownloadArchiveFromNetworkNotice(
                new URL("https://mobilitydata.org")
        );
        underTest.export(toExport);

        verify(mockGenerator, times(1)).writeObject(ArgumentMatchers.eq(toExport));
        verifyNoMoreInteractions(mockGenerator);
    }

    @Test
    void exportCannotParseFloatNoticeShouldWriteObject() throws IOException {
        JsonGenerator mockGenerator = mock(JsonGenerator.class);

        JsonNoticeExporter underTest = new JsonNoticeExporter(mockGenerator);
        CannotParseFloatNotice toExport = new CannotParseFloatNotice(
                FILENAME,
                "field_name",
                666,
                "abc");
        underTest.export(toExport);

        verify(mockGenerator, times(1)).writeObject(ArgumentMatchers.eq(toExport));
        verifyNoMoreInteractions(mockGenerator);
    }

    @Test
    void exportCannotParseIntegerNoticeShouldWriteObject() throws IOException {
        JsonGenerator mockGenerator = mock(JsonGenerator.class);

        JsonNoticeExporter underTest = new JsonNoticeExporter(mockGenerator);
        CannotParseIntegerNotice toExport = new CannotParseIntegerNotice(
                FILENAME,
                "field_name",
                666,
                "abc"
        );
        underTest.export(toExport);

        verify(mockGenerator, times(1)).writeObject(ArgumentMatchers.eq(toExport));
        verifyNoMoreInteractions(mockGenerator);
    }

    @Test
    void exportCannotUnzipInputArchiveNoticeShouldWriteObject() throws IOException {
        JsonGenerator mockGenerator = mock(JsonGenerator.class);

        JsonNoticeExporter underTest = new JsonNoticeExporter(mockGenerator);
        CannotUnzipInputArchiveNotice toExport = new CannotUnzipInputArchiveNotice(FILENAME);
        underTest.export(toExport);

        verify(mockGenerator, times(1)).writeObject(ArgumentMatchers.eq(toExport));
        verifyNoMoreInteractions(mockGenerator);
    }

    @Test
    void exportFloatFieldValueOutOfRangeNoticeShouldWriteObject() throws IOException {
        JsonGenerator mockGenerator = mock(JsonGenerator.class);

        JsonNoticeExporter underTest = new JsonNoticeExporter(mockGenerator);
        FloatFieldValueOutOfRangeNotice toExport = new FloatFieldValueOutOfRangeNotice(
                FILENAME,
                "field_name",
                "entity_id",
                0, 66, 666
        );
        underTest.export(toExport);

        verify(mockGenerator, times(1)).writeObject(ArgumentMatchers.eq(toExport));
        verifyNoMoreInteractions(mockGenerator);
    }

    @Test
    void exportIntegerFieldValueOutOfRangeNoticeShouldWriteObject() throws IOException {
        JsonGenerator mockGenerator = mock(JsonGenerator.class);

        JsonNoticeExporter underTest = new JsonNoticeExporter(mockGenerator);
        IntegerFieldValueOutOfRangeNotice toExport = new IntegerFieldValueOutOfRangeNotice(
                FILENAME,
                "field_name",
                "entity_id",
                0, 66, 666
        );
        underTest.export(toExport);

        verify(mockGenerator, times(1)).writeObject(ArgumentMatchers.eq(toExport));
        verifyNoMoreInteractions(mockGenerator);
    }

    @Test
    void exportInvalidRowLengthNoticeShouldWriteObject() throws IOException {
        JsonGenerator mockGenerator = mock(JsonGenerator.class);

        JsonNoticeExporter underTest = new JsonNoticeExporter(mockGenerator);
        InvalidRowLengthNotice toExport = new InvalidRowLengthNotice(
                FILENAME, 666, 10, 8
        );
        underTest.export(toExport);

        verify(mockGenerator, times(1)).writeObject(ArgumentMatchers.eq(toExport));
        verifyNoMoreInteractions(mockGenerator);
    }

    @Test
    void exportInvalidTimezoneNoticeShouldWriteObject() throws IOException {
        JsonGenerator mockGenerator = mock(JsonGenerator.class);

        JsonNoticeExporter underTest = new JsonNoticeExporter(mockGenerator);
        InvalidTimezoneNotice toExport = new InvalidTimezoneNotice(
                FILENAME,
                "field_name",
                "entity_id",
                "neverland"
        );
        underTest.export(toExport);

        verify(mockGenerator, times(1)).writeObject(ArgumentMatchers.eq(toExport));
        verifyNoMoreInteractions(mockGenerator);
    }

    @Test
    void exportInvalidUrlNoticeShouldWriteObject() throws IOException {
        JsonGenerator mockGenerator = mock(JsonGenerator.class);

        JsonNoticeExporter underTest = new JsonNoticeExporter(mockGenerator);
        InvalidUrlNotice toExport = new InvalidUrlNotice(
                FILENAME,
                "field_name",
                "entity_id",
                "ftp://truc.bidule"
        );
        underTest.export(toExport);

        verify(mockGenerator, times(1)).writeObject(ArgumentMatchers.eq(toExport));
        verifyNoMoreInteractions(mockGenerator);
    }

    @Test
    void exportMissingHeaderNoticeShouldWriteObject() throws IOException {
        JsonGenerator mockGenerator = mock(JsonGenerator.class);

        JsonNoticeExporter underTest = new JsonNoticeExporter(mockGenerator);
        MissingHeaderNotice toExport = new MissingHeaderNotice(FILENAME, "missing_header");
        underTest.export(toExport);

        verify(mockGenerator, times(1)).writeObject(ArgumentMatchers.eq(toExport));
        verifyNoMoreInteractions(mockGenerator);
    }

    @Test
    void exportMissingRequiredFileNoticeShouldWriteObject() throws IOException {
        JsonGenerator mockGenerator = mock(JsonGenerator.class);

        JsonNoticeExporter underTest = new JsonNoticeExporter(mockGenerator);
        MissingRequiredFileNotice toExport = new MissingRequiredFileNotice(FILENAME);
        underTest.export(toExport);

        verify(mockGenerator, times(1)).writeObject(ArgumentMatchers.eq(toExport));
        verifyNoMoreInteractions(mockGenerator);
    }

    @Test
    void exportMissingRequiredValueNoticeShouldWriteObject() throws IOException {
        JsonGenerator mockGenerator = mock(JsonGenerator.class);

        JsonNoticeExporter underTest = new JsonNoticeExporter(mockGenerator);
        MissingRequiredValueNotice toExport = new MissingRequiredValueNotice(
                FILENAME,
                "field_name",
                "entity_id"
        );
        underTest.export(toExport);

        verify(mockGenerator, times(1)).writeObject(ArgumentMatchers.eq(toExport));
        verifyNoMoreInteractions(mockGenerator);
    }

    @Test
    void exportInvalidColorNoticeShouldWriteObject() throws IOException {
        JsonGenerator mockGenerator = mock(JsonGenerator.class);

        JsonNoticeExporter underTest = new JsonNoticeExporter(mockGenerator);
        InvalidColorNotice toExport = new InvalidColorNotice(
                FILENAME,
                "field_name",
                "entity_id",
                "#zz"
        );
        underTest.export(toExport);

        verify(mockGenerator, times(1)).writeObject(ArgumentMatchers.eq(toExport));
        verifyNoMoreInteractions(mockGenerator);
    }

    @Test
    void exportExtraFileFoundNoticeShouldWriteObject() throws IOException {
        JsonGenerator mockGenerator = mock(JsonGenerator.class);

        JsonNoticeExporter underTest = new JsonNoticeExporter(mockGenerator);
        ExtraFileFoundNotice toExport = new ExtraFileFoundNotice(FILENAME);
        underTest.export(toExport);

        verify(mockGenerator, times(1)).writeObject(ArgumentMatchers.eq(toExport));
        verifyNoMoreInteractions(mockGenerator);
    }

    @Test
    void exportInvalidTimeNoticeShouldWriteObject() throws IOException {
        JsonGenerator mockGenerator = mock(JsonGenerator.class);

        JsonNoticeExporter underTest = new JsonNoticeExporter(mockGenerator);
        InvalidTimeNotice toExport = new InvalidTimeNotice(FILENAME, "field_name", "time_value",
                "entity_id");
        underTest.export(toExport);

        verify(mockGenerator, times(1)).writeObject(ArgumentMatchers.eq(toExport));
        verifyNoMoreInteractions(mockGenerator);
    }

    @Test
    void exportCannotParseDateNoticeShouldWriteObject() throws IOException {
        JsonGenerator mockGenerator = mock(JsonGenerator.class);

        JsonNoticeExporter underTest = new JsonNoticeExporter(mockGenerator);
        CannotParseDateNotice toExport = new CannotParseDateNotice(FILENAME, "field_name", 0,
                "raw_value");
        underTest.export(toExport);

        verify(mockGenerator, times(1)).writeObject(ArgumentMatchers.eq(toExport));
        verifyNoMoreInteractions(mockGenerator);
    }

    @Test
    void exportInvalidCurrencyCodeNoticeShouldWriteObject() throws IOException {

        JsonGenerator mockGenerator = mock(JsonGenerator.class);

        JsonNoticeExporter underTest = new JsonNoticeExporter(mockGenerator);
        InvalidCurrencyCodeNotice toExport = new InvalidCurrencyCodeNotice(FILENAME, "field_name", "entity_id",
                "currency_code");
        underTest.export(toExport);

        verify(mockGenerator, times(1)).writeObject(ArgumentMatchers.eq(toExport));
        verifyNoMoreInteractions(mockGenerator);
    }

    @Test
    void exportUnexpectedEnumValueNoticeShouldWriteObject() throws IOException {
        JsonGenerator mockGenerator = mock(JsonGenerator.class);

        JsonNoticeExporter underTest = new JsonNoticeExporter(mockGenerator);
        UnexpectedEnumValueNotice toExport = new UnexpectedEnumValueNotice(FILENAME, "field_name",
                "entity_id", 2);
        underTest.export(toExport);

        verify(mockGenerator, times(1)).writeObject(ArgumentMatchers.eq(toExport));
        verifyNoMoreInteractions(mockGenerator);
    }

    @Test
    void exportIllegalFieldValueCombinationNoticeShouldWriteObject() throws IOException {
        JsonGenerator mockGenerator = mock(JsonGenerator.class);

        JsonNoticeExporter underTest = new JsonNoticeExporter(mockGenerator);
        IllegalFieldValueCombination toExport = new IllegalFieldValueCombination(FILENAME, "field_name",
                "conflicting_field_name", "entity_id");
        underTest.export(toExport);

        verify(mockGenerator, times(1)).writeObject(ArgumentMatchers.eq(toExport));
        verifyNoMoreInteractions(mockGenerator);
    }

    @Test
    void exportDuplicatedEntityNoticeShouldWriteObject() throws IOException {
        JsonGenerator mockGenerator = mock(JsonGenerator.class);

        JsonNoticeExporter underTest = new JsonNoticeExporter(mockGenerator);
        DuplicatedEntityNotice toExport = new DuplicatedEntityNotice(FILENAME, "field_name",
                "entity_id");
        underTest.export(toExport);

        verify(mockGenerator, times(1)).writeObject(ArgumentMatchers.eq(toExport));
        verifyNoMoreInteractions(mockGenerator);
    }

    @Test
    void exportInvalidLangNoticeShouldWriteObject() throws IOException {
        JsonGenerator mockGenerator = mock(JsonGenerator.class);

        JsonNoticeExporter underTest = new JsonNoticeExporter(mockGenerator);
        InvalidLangNotice toExport = new InvalidLangNotice(FILENAME, "field_name",
                "entity_id", "lang_value");
        underTest.export(toExport);

        verify(mockGenerator, times(1)).writeObject(ArgumentMatchers.eq(toExport));
        verifyNoMoreInteractions(mockGenerator);
    }

    @Test
    void exportInvalidEmailNoticeShouldWriteObject() throws IOException {
        JsonGenerator mockGenerator = mock(JsonGenerator.class);

        JsonNoticeExporter underTest = new JsonNoticeExporter(mockGenerator);
        InvalidEmailNotice toExport = new InvalidEmailNotice(FILENAME, "field_name",
                "entity_id", "email_value");
        underTest.export(toExport);

        verify(mockGenerator, times(1)).writeObject(ArgumentMatchers.eq(toExport));
        verifyNoMoreInteractions(mockGenerator);
    }

    @Test
    void exportRouteDescriptionEqualsNameNoticeShouldWriteObject() throws IOException {
        JsonGenerator mockGenerator = mock(JsonGenerator.class);

        JsonNoticeExporter underTest = new JsonNoticeExporter(mockGenerator);
        SameNameAndDescriptionForRouteNotice toExport = new SameNameAndDescriptionForRouteNotice(FILENAME, "entity_id");
        underTest.export(toExport);

        verify(mockGenerator, times(1)).writeObject(ArgumentMatchers.eq(toExport));
        verifyNoMoreInteractions(mockGenerator);
    }

    @Test
    void exportRouteColorAndTextContrastNoticeShouldWriteObject() throws IOException {
        JsonGenerator mockGenerator = mock(JsonGenerator.class);

        JsonNoticeExporter underTest = new JsonNoticeExporter(mockGenerator);
        RouteColorAndTextInsufficientContrastNotice toExport = new RouteColorAndTextInsufficientContrastNotice(FILENAME,
                "entity_id",
                0d);
        underTest.export(toExport);

        verify(mockGenerator, times(1)).writeObject(ArgumentMatchers.eq(toExport));
        verifyNoMoreInteractions(mockGenerator);
    }

    @Test
    void exportRouteShortNameTooLongNoticeShouldWriteObject() throws IOException {
        JsonGenerator mockGenerator = mock(JsonGenerator.class);

        JsonNoticeExporter underTest = new JsonNoticeExporter(mockGenerator);
        RouteShortNameTooLongNotice toExport = new RouteShortNameTooLongNotice(FILENAME, "entity_id", "13");
        underTest.export(toExport);

        verify(mockGenerator, times(1)).writeObject(ArgumentMatchers.eq(toExport));
        verifyNoMoreInteractions(mockGenerator);
    }

    @Test
    void exportInvalidRouteTypeNoticeShouldWriteObject() throws IOException {
        JsonGenerator mockGenerator = mock(JsonGenerator.class);

        JsonNoticeExporter underTest = new JsonNoticeExporter(mockGenerator);
        InvalidRouteTypeNotice toExport = new InvalidRouteTypeNotice(FILENAME, "entity_id");
        underTest.export(toExport);

        verify(mockGenerator, times(1)).writeObject(ArgumentMatchers.eq(toExport));
        verifyNoMoreInteractions(mockGenerator);
    }

    @Test
    void exportMissingBothRouteNamesNoticeShouldWriteObject() throws IOException {
        JsonGenerator mockGenerator = mock(JsonGenerator.class);

        JsonNoticeExporter underTest = new JsonNoticeExporter(mockGenerator);
        MissingShortAndLongNameForRouteNotice toExport = new MissingShortAndLongNameForRouteNotice(FILENAME, "entity_id");
        underTest.export(toExport);

        verify(mockGenerator, times(1)).writeObject(ArgumentMatchers.eq(toExport));
        verifyNoMoreInteractions(mockGenerator);
    }

    @Test
    void exportMissingRouteShortNameNoticeShouldWriteObject() throws IOException {
        JsonGenerator mockGenerator = mock(JsonGenerator.class);

        JsonNoticeExporter underTest = new JsonNoticeExporter(mockGenerator);
        MissingRouteShortNameNotice toExport = new MissingRouteShortNameNotice(FILENAME, "entity_id");
        underTest.export(toExport);

        verify(mockGenerator, times(1)).writeObject(ArgumentMatchers.eq(toExport));
        verifyNoMoreInteractions(mockGenerator);
    }

    @Test
    void exportMissingRouteLongNameNoticeShouldWriteObject() throws IOException {
        JsonGenerator mockGenerator = mock(JsonGenerator.class);

        JsonNoticeExporter underTest = new JsonNoticeExporter(mockGenerator);
        MissingRouteLongNameNotice toExport = new MissingRouteLongNameNotice(FILENAME, "entity_id");
        underTest.export(toExport);

        verify(mockGenerator, times(1)).writeObject(ArgumentMatchers.eq(toExport));
        verifyNoMoreInteractions(mockGenerator);
    }

    @Test
    void exportRouteLongNameEqualsShortNameNoticeShouldWriteObject() throws IOException {
        JsonGenerator mockGenerator = mock(JsonGenerator.class);

        JsonNoticeExporter underTest = new JsonNoticeExporter(mockGenerator);
        RouteLongNameEqualsShortNameNotice toExport = new RouteLongNameEqualsShortNameNotice(FILENAME, "entity_id");
        underTest.export(toExport);

        verify(mockGenerator, times(1)).writeObject(ArgumentMatchers.eq(toExport));
        verifyNoMoreInteractions(mockGenerator);
    }

    @Test
    void exportRouteLongNameContainsShortNameNoticeShouldWriteObject() throws IOException {
        JsonGenerator mockGenerator = mock(JsonGenerator.class);

        JsonNoticeExporter underTest = new JsonNoticeExporter(mockGenerator);
        RouteLongNameContainsShortNameNotice toExport = new RouteLongNameContainsShortNameNotice(FILENAME, "entity_id");
        underTest.export(toExport);

        verify(mockGenerator, times(1)).writeObject(ArgumentMatchers.eq(toExport));
        verifyNoMoreInteractions(mockGenerator);
    }

    @Test
    void exportCalendarEndDateBeforeStartDateShouldWriteObject() throws IOException {
        JsonGenerator mockGenerator = mock(JsonGenerator.class);

        JsonNoticeExporter underTest = new JsonNoticeExporter(mockGenerator);
        CalendarEndDateBeforeStartDateNotice toExport = new CalendarEndDateBeforeStartDateNotice(
                FILENAME,
                "wkend",
                LocalDate.of(2020, 2, 1),
                LocalDate.of(2020, 1, 1)
        );
        underTest.export(toExport);

        verify(mockGenerator, times(1)).writeObject(ArgumentMatchers.eq(toExport));
        verifyNoMoreInteractions(mockGenerator);
    }
}