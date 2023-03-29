// 
// Decompiled by Procyon v0.5.36
// 

package io;

import java.io.IOException;
import javax.xml.stream.XMLStreamException;
import java.util.Iterator;
import javax.xml.stream.XMLStreamWriter;
import java.io.OutputStream;
import java.io.FileOutputStream;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.stream.XMLEventReader;
import java.io.InputStream;
import java.io.FileInputStream;
import javax.xml.stream.XMLInputFactory;
import java.io.File;
import gameObjects.Constants;
import java.util.ArrayList;

public class XMLParser
{
    public static ArrayList<ScoreData> readFile() {
        final ArrayList<ScoreData> dataList = new ArrayList<ScoreData>();
        try {
            final File file = new File(Constants.SCORE_PATH);
            if (!file.exists() || file.length() == 0L) {
                return dataList;
            }
            final XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            final XMLEventReader eventReader = inputFactory.createXMLEventReader(new FileInputStream(file));
            ScoreData data = null;
            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();
                if (event.isStartElement()) {
                    final StartElement start = event.asStartElement();
                    if (start.getName().getLocalPart().equals("PLAYER")) {
                        data = new ScoreData();
                    }
                    else if (start.getName().getLocalPart().equals("DATE")) {
                        event = eventReader.nextEvent();
                        data.setDate(event.asCharacters().getData());
                    }
                    else if (start.getName().getLocalPart().equals("SCORE")) {
                        event = eventReader.nextEvent();
                        data.setScore(Integer.parseInt(event.asCharacters().getData()));
                    }
                }
                if (event.isEndElement()) {
                    final EndElement end = event.asEndElement();
                    if (!end.getName().getLocalPart().equals("PLAYER")) {
                        continue;
                    }
                    dataList.add(data);
                }
            }
            return dataList;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static void writeFile(final ArrayList<ScoreData> dataList) throws XMLStreamException, IOException {
        final XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
        final File outputFile = new File(Constants.SCORE_PATH);
        outputFile.getParentFile().mkdir();
        outputFile.createNewFile();
        final XMLStreamWriter writer = outputFactory.createXMLStreamWriter(new FileOutputStream(outputFile), "UTF-8");
        writer.writeStartDocument();
        writer.writeStartElement("PLAYERS");
        for (final ScoreData data : dataList) {
            writer.writeStartElement("PLAYER");
            writer.writeStartElement("DATE");
            writer.writeCharacters(data.getDate());
            writer.writeEndElement();
            writer.writeStartElement("SCORE");
            writer.writeCharacters(Integer.toString(data.getScore()));
            writer.writeEndElement();
            writer.writeEndElement();
        }
        writer.writeEndElement();
        writer.flush();
        writer.close();
    }
}
