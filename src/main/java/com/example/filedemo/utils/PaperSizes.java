package com.example.filedemo.utils;

import java.awt.print.PageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;

public class PaperSizes {

    private Map<String, MarginPaper> paperDefinitions = new HashMap<>();
    private ArrayList<String> paperList = new ArrayList<>();

    private static final double mmToSubInch = 72 / 25.4;

    private final Map<String, String> paperNames = new HashMap<>();

    //default for paper selection
    private int defaultPageIndex;
    private String defaultSize;

    private PrintService printService;

    public PaperSizes(final String defaultSize) {
        this.defaultSize = defaultSize;
        populateNameMap();
    }

    public String[] getAvailablePaperSizes() {
        final Object[] objs = paperList.toArray();
        final String[] names = new String[objs.length];
        for (int i = 0; i < objs.length; i++) {
            names[i] = (String) objs[i];
        }
        return names;
    }

    //return selected Paper
    public MarginPaper getSelectedPaper(final String id) {
        return paperDefinitions.get(id);
    }

    /**
     * Checks flags and locale and chooses a default paper size
     */
    private void setDefault() {

        if (paperList == null) {
            return;
        }

        //set default value
        defaultPageIndex = -1;

        //check JVM flag
        final String paperSizeFlag = System.getProperty("org.jpedal.printPaperSize");
        if (paperSizeFlag != null) {
            for (int i = 0; i < paperList.size(); i++) {
                if (paperList.get(i).equals(paperSizeFlag)) {
                    defaultPageIndex = i;
                }
            }
        }

        //Check properties file value (passed in)
        if (defaultPageIndex == -1 && defaultSize != null && !defaultSize.isEmpty()) {
            for (int i = 0; i < paperList.size(); i++) {
                if (defaultSize.equals(paperList.get(i))) {
                    defaultPageIndex = i;
                }
            }
        }

        //if no default specified check location and choose
        if (defaultPageIndex == -1) {
            defaultSize = "A4";

            //Check for US countries
            final String[] letterSizeDefaults = {"US", "CA", "MX", "CO", "VE", "AR", "CL", "PH"};
            final String country = Locale.getDefault().getCountry();
            for (final String letterSizeDefault : letterSizeDefaults) {
                if (country.equals(letterSizeDefault)) {
                    defaultSize = "North American Letter";
                }
            }

            //Get index
            for (int j = 0; j < paperList.size(); j++) {
                if (defaultSize.equals(paperList.get(j))) {
                    defaultPageIndex = j;
                }
            }

            //Make sure not negative
            if (defaultPageIndex == -1) {
                defaultPageIndex = 0;
            }
        }
    }

    /**
     * Sets the print service and checks which page sizes are available
     *
     * @param p print service
     */
    public synchronized void setPrintService(final PrintService p) {
        this.printService = p;
        paperDefinitions = new HashMap<>();
        paperList = new ArrayList<>();

        checkAndAddSize(MediaSizeName.ISO_A4);
        checkAndAddSize(MediaSizeName.NA_LETTER);
        checkAndAddSize(MediaSizeName.ISO_A0);
        checkAndAddSize(MediaSizeName.ISO_A1);
        checkAndAddSize(MediaSizeName.ISO_A2);
        checkAndAddSize(MediaSizeName.ISO_A3);
        checkAndAddSize(MediaSizeName.ISO_A5);
        checkAndAddSize(MediaSizeName.ISO_A6);
        checkAndAddSize(MediaSizeName.ISO_A7);
        checkAndAddSize(MediaSizeName.ISO_A8);
        checkAndAddSize(MediaSizeName.ISO_A9);
        checkAndAddSize(MediaSizeName.ISO_A10);
        checkAndAddSize(MediaSizeName.ISO_B0);
        checkAndAddSize(MediaSizeName.ISO_B1);
        checkAndAddSize(MediaSizeName.ISO_B2);
        checkAndAddSize(MediaSizeName.ISO_B3);
        checkAndAddSize(MediaSizeName.ISO_B4);
        checkAndAddSize(MediaSizeName.ISO_B5);
        checkAndAddSize(MediaSizeName.ISO_B6);
        checkAndAddSize(MediaSizeName.ISO_B7);
        checkAndAddSize(MediaSizeName.ISO_B8);
        checkAndAddSize(MediaSizeName.ISO_B9);
        checkAndAddSize(MediaSizeName.ISO_B10);
        checkAndAddSize(MediaSizeName.JIS_B0);
        checkAndAddSize(MediaSizeName.JIS_B1);
        checkAndAddSize(MediaSizeName.JIS_B2);
        checkAndAddSize(MediaSizeName.JIS_B3);
        checkAndAddSize(MediaSizeName.JIS_B4);
        checkAndAddSize(MediaSizeName.JIS_B5);
        checkAndAddSize(MediaSizeName.JIS_B6);
        checkAndAddSize(MediaSizeName.JIS_B7);
        checkAndAddSize(MediaSizeName.JIS_B8);
        checkAndAddSize(MediaSizeName.JIS_B9);
        checkAndAddSize(MediaSizeName.JIS_B10);
        checkAndAddSize(MediaSizeName.ISO_C0);
        checkAndAddSize(MediaSizeName.ISO_C1);
        checkAndAddSize(MediaSizeName.ISO_C2);
        checkAndAddSize(MediaSizeName.ISO_C3);
        checkAndAddSize(MediaSizeName.ISO_C4);
        checkAndAddSize(MediaSizeName.ISO_C5);
        checkAndAddSize(MediaSizeName.ISO_C6);
        checkAndAddSize(MediaSizeName.NA_LEGAL);
        checkAndAddSize(MediaSizeName.EXECUTIVE);
        checkAndAddSize(MediaSizeName.LEDGER);
        checkAndAddSize(MediaSizeName.TABLOID);
        checkAndAddSize(MediaSizeName.INVOICE);
        checkAndAddSize(MediaSizeName.FOLIO);
        checkAndAddSize(MediaSizeName.QUARTO);
        checkAndAddSize(MediaSizeName.JAPANESE_POSTCARD);
        checkAndAddSize(MediaSizeName.JAPANESE_DOUBLE_POSTCARD);
        checkAndAddSize(MediaSizeName.A);
        checkAndAddSize(MediaSizeName.B);
        checkAndAddSize(MediaSizeName.C);
        checkAndAddSize(MediaSizeName.D);
        checkAndAddSize(MediaSizeName.E);
        checkAndAddSize(MediaSizeName.ISO_DESIGNATED_LONG);
        checkAndAddSize(MediaSizeName.ITALY_ENVELOPE);
        checkAndAddSize(MediaSizeName.MONARCH_ENVELOPE);
        checkAndAddSize(MediaSizeName.PERSONAL_ENVELOPE);
        checkAndAddSize(MediaSizeName.NA_NUMBER_9_ENVELOPE);
        checkAndAddSize(MediaSizeName.NA_NUMBER_10_ENVELOPE);
        checkAndAddSize(MediaSizeName.NA_NUMBER_11_ENVELOPE);
        checkAndAddSize(MediaSizeName.NA_NUMBER_12_ENVELOPE);
        checkAndAddSize(MediaSizeName.NA_NUMBER_14_ENVELOPE);
        checkAndAddSize(MediaSizeName.NA_6X9_ENVELOPE);
        checkAndAddSize(MediaSizeName.NA_7X9_ENVELOPE);
        checkAndAddSize(MediaSizeName.NA_9X11_ENVELOPE);
        checkAndAddSize(MediaSizeName.NA_9X12_ENVELOPE);
        checkAndAddSize(MediaSizeName.NA_10X13_ENVELOPE);
        checkAndAddSize(MediaSizeName.NA_10X14_ENVELOPE);
        checkAndAddSize(MediaSizeName.NA_10X15_ENVELOPE);
        checkAndAddSize(MediaSizeName.NA_5X7);
        checkAndAddSize(MediaSizeName.NA_8X10);

        setDefault();
    }

    public String[] getPaperSizes() {

        return new String[]{
                paperNames.get(MediaSizeName.ISO_A4.toString()),
                paperNames.get(MediaSizeName.NA_LETTER.toString()),
                paperNames.get(MediaSizeName.ISO_A0.toString()),
                paperNames.get(MediaSizeName.ISO_A1.toString()),
                paperNames.get(MediaSizeName.ISO_A2.toString()),
                paperNames.get(MediaSizeName.ISO_A3.toString()),
                paperNames.get(MediaSizeName.ISO_A5.toString()),
                paperNames.get(MediaSizeName.ISO_A6.toString()),
                paperNames.get(MediaSizeName.ISO_A7.toString()),
                paperNames.get(MediaSizeName.ISO_A8.toString()),
                paperNames.get(MediaSizeName.ISO_A9.toString()),
                paperNames.get(MediaSizeName.ISO_A10.toString()),
                paperNames.get(MediaSizeName.ISO_B0.toString()),
                paperNames.get(MediaSizeName.ISO_B1.toString()),
                paperNames.get(MediaSizeName.ISO_B2.toString()),
                paperNames.get(MediaSizeName.ISO_B3.toString()),
                paperNames.get(MediaSizeName.ISO_B4.toString()),
                paperNames.get(MediaSizeName.ISO_B5.toString()),
                paperNames.get(MediaSizeName.ISO_B6.toString()),
                paperNames.get(MediaSizeName.ISO_B7.toString()),
                paperNames.get(MediaSizeName.ISO_B8.toString()),
                paperNames.get(MediaSizeName.ISO_B9.toString()),
                paperNames.get(MediaSizeName.ISO_B10.toString()),
                paperNames.get(MediaSizeName.JIS_B0.toString()),
                paperNames.get(MediaSizeName.JIS_B1.toString()),
                paperNames.get(MediaSizeName.JIS_B2.toString()),
                paperNames.get(MediaSizeName.JIS_B3.toString()),
                paperNames.get(MediaSizeName.JIS_B4.toString()),
                paperNames.get(MediaSizeName.JIS_B5.toString()),
                paperNames.get(MediaSizeName.JIS_B6.toString()),
                paperNames.get(MediaSizeName.JIS_B7.toString()),
                paperNames.get(MediaSizeName.JIS_B8.toString()),
                paperNames.get(MediaSizeName.JIS_B9.toString()),
                paperNames.get(MediaSizeName.JIS_B10.toString()),
                paperNames.get(MediaSizeName.ISO_C0.toString()),
                paperNames.get(MediaSizeName.ISO_C1.toString()),
                paperNames.get(MediaSizeName.ISO_C2.toString()),
                paperNames.get(MediaSizeName.ISO_C3.toString()),
                paperNames.get(MediaSizeName.ISO_C4.toString()),
                paperNames.get(MediaSizeName.ISO_C5.toString()),
                paperNames.get(MediaSizeName.ISO_C6.toString()),
                paperNames.get(MediaSizeName.NA_LEGAL.toString()),
                paperNames.get(MediaSizeName.EXECUTIVE.toString()),
                paperNames.get(MediaSizeName.LEDGER.toString()),
                paperNames.get(MediaSizeName.TABLOID.toString()),
                paperNames.get(MediaSizeName.INVOICE.toString()),
                paperNames.get(MediaSizeName.FOLIO.toString()),
                paperNames.get(MediaSizeName.QUARTO.toString()),
                paperNames.get(MediaSizeName.JAPANESE_POSTCARD.toString()),
                paperNames.get(MediaSizeName.JAPANESE_DOUBLE_POSTCARD.toString()),
                paperNames.get(MediaSizeName.A.toString()),
                paperNames.get(MediaSizeName.B.toString()),
                paperNames.get(MediaSizeName.C.toString()),
                paperNames.get(MediaSizeName.D.toString()),
                paperNames.get(MediaSizeName.E.toString()),
                paperNames.get(MediaSizeName.ISO_DESIGNATED_LONG.toString()),
                paperNames.get(MediaSizeName.ITALY_ENVELOPE.toString()),
                paperNames.get(MediaSizeName.MONARCH_ENVELOPE.toString()),
                paperNames.get(MediaSizeName.PERSONAL_ENVELOPE.toString()),
                paperNames.get(MediaSizeName.NA_NUMBER_9_ENVELOPE.toString()),
                paperNames.get(MediaSizeName.NA_NUMBER_10_ENVELOPE.toString()),
                paperNames.get(MediaSizeName.NA_NUMBER_11_ENVELOPE.toString()),
                paperNames.get(MediaSizeName.NA_NUMBER_12_ENVELOPE.toString()),
                paperNames.get(MediaSizeName.NA_NUMBER_14_ENVELOPE.toString()),
                paperNames.get(MediaSizeName.NA_6X9_ENVELOPE.toString()),
                paperNames.get(MediaSizeName.NA_7X9_ENVELOPE.toString()),
                paperNames.get(MediaSizeName.NA_9X11_ENVELOPE.toString()),
                paperNames.get(MediaSizeName.NA_9X12_ENVELOPE.toString()),
                paperNames.get(MediaSizeName.NA_10X13_ENVELOPE.toString()),
                paperNames.get(MediaSizeName.NA_10X14_ENVELOPE.toString()),
                paperNames.get(MediaSizeName.NA_10X15_ENVELOPE.toString()),
                paperNames.get(MediaSizeName.NA_5X7.toString()),
                paperNames.get(MediaSizeName.NA_8X10.toString())};
    }

    /**
     * Checks whether a paper size is available and adds it to the array
     *
     * @param name The MediaSizeName to check
     */
    private void checkAndAddSize(final MediaSizeName name) {

        //Check if available on this printer
        final PrintRequestAttributeSet attributes = new HashPrintRequestAttributeSet();
        if (!printService.isAttributeValueSupported(name, new DocFlavor.BYTE_ARRAY(DocFlavor.BYTE_ARRAY.PNG.getMimeType()), attributes)) {
            return;
        }


        //Get name and lookup in our name map
        final String o = paperNames.get(name.toString());
        final String printDescription;
        if (o != null) {
            printDescription = o;
        } else {
            printDescription = name.toString();
        }


        //Get paper size
        final MediaSize size = MediaSize.getMediaSizeForName(name);
        double pX = size.getX(MediaSize.MM);
        double pY = size.getY(MediaSize.MM);


        //Get printable area
        attributes.add(name);
        final MediaPrintableArea[] area = (MediaPrintableArea[]) printService.getSupportedAttributeValues(MediaPrintableArea.class, null, attributes);

        if (area.length == 0) {
            return;
        }

        int useArea = 0;
        if (area[useArea] == null) {
            for (int i = 0; i != area.length && area[useArea] == null; i++) {
                useArea = i;
            }
        }


        final float[] values = area[useArea].getPrintableArea(MediaPrintableArea.MM);

        //Check if very near to pagesize since pagesize is stored less accurately (avoids rounding/negative issues)
        if (values[2] > pX - 0.5 && values[2] < pX + 0.5) {
            values[2] = (float) pX;
        }
        if (values[3] > pY - 0.5 && values[3] < pY + 0.5) {
            values[3] = (float) pY;
        }

        //Check if printer thinks page is other way round - flip pagesize if so
        if (values[2] > pX ^ values[3] > pY) {
            final double temp = pX;
            pX = pY;
            pY = temp;
        }


        //Create and store as Paper object
        final MarginPaper paper = new MarginPaper();
        paper.setSize(pX * mmToSubInch, pY * mmToSubInch);
        paper.setMinImageableArea(values[0] * mmToSubInch, values[1] * mmToSubInch, values[2] * mmToSubInch, values[3] * mmToSubInch);

        paperDefinitions.put(printDescription, paper);
        paperList.add(printDescription);
    }

    /**
     * Returns the index of the default paper size
     *
     * @return
     */
    public int getDefaultPageIndex() {
        return defaultPageIndex;

    }

    /**
     * Returns the default orientation requested by the printer
     *
     * @return int flag for the orientation
     */
    public int getDefaultPageOrientation() {

        //Set the pageformat orientation based on printer preference
        final OrientationRequested or = (OrientationRequested) printService.getDefaultAttributeValue(OrientationRequested.class);
        int orientation = PageFormat.PORTRAIT;
        if (or != null) {
            switch (or.getValue()) {
                case 4:
                    orientation = PageFormat.LANDSCAPE;
                    break;
                case 5:
                    orientation = PageFormat.REVERSE_LANDSCAPE;
                    break;
            }
        }

        return orientation;
    }

    /**
     * Fills the name map from standardised to "pretty" names
     */
    private void populateNameMap() {
        paperNames.put("iso-a0", "A0");
        paperNames.put("iso-a1", "A1");
        paperNames.put("iso-a2", "A2");
        paperNames.put("iso-a3", "A3");
        paperNames.put("iso-a4", "A4");
        paperNames.put("iso-a5", "A5");
        paperNames.put("iso-a6", "A6");
        paperNames.put("iso-a7", "A7");
        paperNames.put("iso-a8", "A8");
        paperNames.put("iso-a9", "A9");
        paperNames.put("iso-a10", "A10");
        paperNames.put("iso-b0", "B0");
        paperNames.put("iso-b1", "B1");
        paperNames.put("iso-b2", "B2");
        paperNames.put("iso-b3", "B3");
        paperNames.put("iso-b4", "B4");
        paperNames.put("iso-b5", "B5");
        paperNames.put("iso-b6", "B6");
        paperNames.put("iso-b7", "B7");
        paperNames.put("iso-b8", "B8");
        paperNames.put("iso-b9", "B9");
        paperNames.put("iso-b10", "B10");
        paperNames.put("na-letter", "North American Letter");
        paperNames.put("na-legal", "North American Legal");
        paperNames.put("na-8x10", "North American 8x10 inch");
        paperNames.put("na-5x7", "North American 5x7 inch");
        paperNames.put("executive", "Executive");
        paperNames.put("folio", "Folio");
        paperNames.put("invoice", "Invoice");
        paperNames.put("tabloid", "Tabloid");
        paperNames.put("ledger", "Ledger");
        paperNames.put("quarto", "Quarto");
        paperNames.put("iso-c0", "C0");
        paperNames.put("iso-c1", "C1");
        paperNames.put("iso-c2", "C2");
        paperNames.put("iso-c3", "C3");
        paperNames.put("iso-c4", "C4");
        paperNames.put("iso-c5", "C5");
        paperNames.put("iso-c6", "C6");
        paperNames.put("iso-designated-long", "ISO Designated Long size");
        paperNames.put("na-10x13-envelope", "North American 10x13 inch");
        paperNames.put("na-9x12-envelope", "North American 9x12 inch");
        paperNames.put("na-number-10-envelope", "North American number 10 business envelope");
        paperNames.put("na-7x9-envelope", "North American 7x9 inch envelope");
        paperNames.put("na-9x11-envelope", "North American 9x11 inch envelope");
        paperNames.put("na-10x14-envelope", "North American 10x14 inch envelope");
        paperNames.put("na-number-9-envelope", "North American number 9 business envelope");
        paperNames.put("na-6x9-envelope", "North American 6x9 inch envelope");
        paperNames.put("na-10x15-envelope", "North American 10x15 inch envelope");
        paperNames.put("monarch-envelope", "Monarch envelope");
        paperNames.put("jis-b0", "Japanese B0");
        paperNames.put("jis-b1", "Japanese B1");
        paperNames.put("jis-b2", "Japanese B2");
        paperNames.put("jis-b3", "Japanese B3");
        paperNames.put("jis-b4", "Japanese B4");
        paperNames.put("jis-b5", "Japanese B5");
        paperNames.put("jis-b6", "Japanese B6");
        paperNames.put("jis-b7", "Japanese B7");
        paperNames.put("jis-b8", "Japanese B8");
        paperNames.put("jis-b9", "Japanese B9");
        paperNames.put("jis-b10", "Japanese B10");
        paperNames.put("a", "Engineering ANSI A");
        paperNames.put("b", "Engineering ANSI B");
        paperNames.put("c", "Engineering ANSI C");
        paperNames.put("d", "Engineering ANSI D");
        paperNames.put("e", "Engineering ANSI E");
        paperNames.put("arch-a", "Architectural A");
        paperNames.put("arch-b", "Architectural B");
        paperNames.put("arch-c", "Architectural C");
        paperNames.put("arch-d", "Architectural D");
        paperNames.put("arch-e", "Architectural E");
        paperNames.put("japanese-postcard", "Japanese Postcard");
        paperNames.put("oufuko-postcard", "Oufuko Postcard");
        paperNames.put("italian-envelope", "Italian Envelope");
        paperNames.put("personal-envelope", "Personal Envelope");
        paperNames.put("na-number-11-envelope", "North American Number 11 Envelope");
        paperNames.put("na-number-12-envelope", "North American Number 12 Envelope");
        paperNames.put("na-number-14-envelope", "North American Number 14 Envelope");
    }
}
