package com.caprispine.caprispine.Util;

import com.caprispine.caprispine.pojo.graph.DateValue;
import com.caprispine.caprispine.pojo.graph.GraphPOJO;
import com.caprispine.caprispine.pojo.graph.GraphResultPOJO;
import com.caprispine.caprispine.pojo.patientassessment.motor.HipExamPOJO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by sunil on 12-04-2018.
 */

public class MotorExamGraph {

    public static GraphPOJO getHipRightDateValueList(List<HipExamPOJO> hipExamPOJOS) {
        Collections.reverse(hipExamPOJOS);
        GraphPOJO graphPOJO = new GraphPOJO();
        List<DateValue> dateValues1 = new ArrayList<>();
        List<DateValue> dateValues2 = new ArrayList<>();
        List<DateValue> dateValues3 = new ArrayList<>();
        List<DateValue> dateValues4 = new ArrayList<>();
        List<DateValue> dateValues5 = new ArrayList<>();
        List<DateValue> dateValues6 = new ArrayList<>();

        for (HipExamPOJO hipExamPOJO : hipExamPOJOS) {
            DateValue dateValue1 = new DateValue(hipExamPOJO.getDate(), hipExamPOJO.getRightRom1());
            DateValue dateValue2 = new DateValue(hipExamPOJO.getDate(), hipExamPOJO.getRightRom2());
            DateValue dateValue3 = new DateValue(hipExamPOJO.getDate(), hipExamPOJO.getRightRom3());
            DateValue dateValue4 = new DateValue(hipExamPOJO.getDate(), hipExamPOJO.getRightRom4());
            DateValue dateValue5 = new DateValue(hipExamPOJO.getDate(), hipExamPOJO.getRightRom5());
            DateValue dateValue6 = new DateValue(hipExamPOJO.getDate(), hipExamPOJO.getRightRom6());

            dateValues1.add(dateValue1);
            dateValues2.add(dateValue2);
            dateValues3.add(dateValue3);
            dateValues4.add(dateValue4);
            dateValues5.add(dateValue5);
            dateValues6.add(dateValue6);
        }

        GraphResultPOJO graphResultPOJO1 = graphResultPOJO("Flexion", dateValues1);
        GraphResultPOJO graphResultPOJO2 = graphResultPOJO("Extension", dateValues2);
        GraphResultPOJO graphResultPOJO3 = graphResultPOJO("External Rotation", dateValues3);
        GraphResultPOJO graphResultPOJO4 = graphResultPOJO("Internal Rotation", dateValues4);
        GraphResultPOJO graphResultPOJO5 = graphResultPOJO("Abduction", dateValues5);
        GraphResultPOJO graphResultPOJO6 = graphResultPOJO("Adduction", dateValues6);

        List<GraphResultPOJO> graphResultPOJOS = new ArrayList<>();
        graphResultPOJOS.add(graphResultPOJO1);
        graphResultPOJOS.add(graphResultPOJO2);
        graphResultPOJOS.add(graphResultPOJO3);
        graphResultPOJOS.add(graphResultPOJO4);
        graphResultPOJOS.add(graphResultPOJO5);
        graphResultPOJOS.add(graphResultPOJO6);

        graphPOJO.setGraphResultPOJOS(graphResultPOJOS);

        return graphPOJO;
    }

    public static GraphPOJO getKneeRightDateValueList(List<HipExamPOJO> hipExamPOJOS) {
        Collections.reverse(hipExamPOJOS);
        GraphPOJO graphPOJO = new GraphPOJO();
        List<DateValue> dateValues1 = new ArrayList<>();
        List<DateValue> dateValues2 = new ArrayList<>();


        for (HipExamPOJO hipExamPOJO: hipExamPOJOS) {
            DateValue dateValue1 = new DateValue(hipExamPOJO.getDate(), hipExamPOJO.getRightRom1());
            DateValue dateValue2 = new DateValue(hipExamPOJO.getDate(), hipExamPOJO.getRightRom2());

            dateValues1.add(dateValue1);
            dateValues2.add(dateValue2);

        }

        GraphResultPOJO graphResultPOJO1 = graphResultPOJO("Flexion", dateValues1);
        GraphResultPOJO graphResultPOJO2 = graphResultPOJO("Extension", dateValues2);

        List<GraphResultPOJO> graphResultPOJOS = new ArrayList<>();
        graphResultPOJOS.add(graphResultPOJO1);
        graphResultPOJOS.add(graphResultPOJO2);


        graphPOJO.setGraphResultPOJOS(graphResultPOJOS);

        return graphPOJO;
    }

    public static GraphPOJO getAnkleRightDateValueList(List<HipExamPOJO> hipExamPOJOS) {
        Collections.reverse(hipExamPOJOS);
        GraphPOJO graphPOJO = new GraphPOJO();
        List<DateValue> dateValues1 = new ArrayList<>();
        List<DateValue> dateValues2 = new ArrayList<>();
        List<DateValue> dateValues3 = new ArrayList<>();
        List<DateValue> dateValues4 = new ArrayList<>();


        for (HipExamPOJO hipExamPOJO: hipExamPOJOS) {
            DateValue dateValue1 = new DateValue(hipExamPOJO.getDate(), hipExamPOJO.getRightRom1());
            DateValue dateValue2 = new DateValue(hipExamPOJO.getDate(), hipExamPOJO.getRightRom2());
            DateValue dateValue3 = new DateValue(hipExamPOJO.getDate(), hipExamPOJO.getRightRom3());
            DateValue dateValue4 = new DateValue(hipExamPOJO.getDate(), hipExamPOJO.getRightRom4());

            dateValues1.add(dateValue1);
            dateValues2.add(dateValue2);
            dateValues3.add(dateValue3);
            dateValues4.add(dateValue4);

        }

        GraphResultPOJO graphResultPOJO1 = graphResultPOJO("Plantar Flexion", dateValues1);
        GraphResultPOJO graphResultPOJO2 = graphResultPOJO("Dorsi Flexion", dateValues2);
        GraphResultPOJO graphResultPOJO3 = graphResultPOJO("Eversion", dateValues3);
        GraphResultPOJO graphResultPOJO4 = graphResultPOJO("Iversion", dateValues4);

        List<GraphResultPOJO> graphResultPOJOS = new ArrayList<>();
        graphResultPOJOS.add(graphResultPOJO1);
        graphResultPOJOS.add(graphResultPOJO2);
        graphResultPOJOS.add(graphResultPOJO3);
        graphResultPOJOS.add(graphResultPOJO4);


        graphPOJO.setGraphResultPOJOS(graphResultPOJOS);

        return graphPOJO;
    }

    public static GraphPOJO getToesRightDateValueList(List<HipExamPOJO> hipExamPOJOS) {
        Collections.reverse(hipExamPOJOS);
        GraphPOJO graphPOJO = new GraphPOJO();
        List<DateValue> dateValues1 = new ArrayList<>();
        List<DateValue> dateValues2 = new ArrayList<>();


        for (HipExamPOJO hipExamPOJO: hipExamPOJOS) {
            DateValue dateValue1 = new DateValue(hipExamPOJO.getDate(), hipExamPOJO.getRightRom1());
            DateValue dateValue2 = new DateValue(hipExamPOJO.getDate(), hipExamPOJO.getRightRom2());

            dateValues1.add(dateValue1);
            dateValues2.add(dateValue2);

        }

        GraphResultPOJO graphResultPOJO1 = graphResultPOJO("Flexion", dateValues1);
        GraphResultPOJO graphResultPOJO2 = graphResultPOJO("Extension", dateValues2);

        List<GraphResultPOJO> graphResultPOJOS = new ArrayList<>();
        graphResultPOJOS.add(graphResultPOJO1);
        graphResultPOJOS.add(graphResultPOJO2);


        graphPOJO.setGraphResultPOJOS(graphResultPOJOS);

        return graphPOJO;
    }

    public static GraphPOJO getShoulderRightDateValueList(List<HipExamPOJO> hipExamPOJOS) {
        Collections.reverse(hipExamPOJOS);
        GraphPOJO graphPOJO = new GraphPOJO();
        List<DateValue> dateValues1 = new ArrayList<>();
        List<DateValue> dateValues2 = new ArrayList<>();
        List<DateValue> dateValues3 = new ArrayList<>();
        List<DateValue> dateValues4 = new ArrayList<>();
        List<DateValue> dateValues5 = new ArrayList<>();
        List<DateValue> dateValues6 = new ArrayList<>();

        for (HipExamPOJO hipExamPOJO : hipExamPOJOS) {
            DateValue dateValue1 = new DateValue(hipExamPOJO.getDate(), hipExamPOJO.getRightRom1());
            DateValue dateValue2 = new DateValue(hipExamPOJO.getDate(), hipExamPOJO.getRightRom2());
            DateValue dateValue3 = new DateValue(hipExamPOJO.getDate(), hipExamPOJO.getRightRom3());
            DateValue dateValue4 = new DateValue(hipExamPOJO.getDate(), hipExamPOJO.getRightRom4());
            DateValue dateValue5 = new DateValue(hipExamPOJO.getDate(), hipExamPOJO.getRightRom5());
            DateValue dateValue6 = new DateValue(hipExamPOJO.getDate(), hipExamPOJO.getRightRom6());

            dateValues1.add(dateValue1);
            dateValues2.add(dateValue2);
            dateValues3.add(dateValue3);
            dateValues4.add(dateValue4);
            dateValues5.add(dateValue5);
            dateValues6.add(dateValue6);
        }

        GraphResultPOJO graphResultPOJO1 = graphResultPOJO("Flexion", dateValues1);
        GraphResultPOJO graphResultPOJO2 = graphResultPOJO("Extension", dateValues2);
        GraphResultPOJO graphResultPOJO3 = graphResultPOJO("External Rotation", dateValues3);
        GraphResultPOJO graphResultPOJO4 = graphResultPOJO("Internal Rotation", dateValues4);
        GraphResultPOJO graphResultPOJO5 = graphResultPOJO("Abduction", dateValues5);
        GraphResultPOJO graphResultPOJO6 = graphResultPOJO("Adduction", dateValues6);

        List<GraphResultPOJO> graphResultPOJOS = new ArrayList<>();
        graphResultPOJOS.add(graphResultPOJO1);
        graphResultPOJOS.add(graphResultPOJO2);
        graphResultPOJOS.add(graphResultPOJO3);
        graphResultPOJOS.add(graphResultPOJO4);
        graphResultPOJOS.add(graphResultPOJO5);
        graphResultPOJOS.add(graphResultPOJO6);

        graphPOJO.setGraphResultPOJOS(graphResultPOJOS);

        return graphPOJO;
    }

    public static GraphPOJO getForearmRightDateValueList(List<HipExamPOJO> hipExamPOJOS) {
        Collections.reverse(hipExamPOJOS);
        GraphPOJO graphPOJO = new GraphPOJO();
        List<DateValue> dateValues1 = new ArrayList<>();
        List<DateValue> dateValues2 = new ArrayList<>();


        for (HipExamPOJO hipExamPOJO: hipExamPOJOS) {
            DateValue dateValue1 = new DateValue(hipExamPOJO.getDate(), hipExamPOJO.getRightRom1());
            DateValue dateValue2 = new DateValue(hipExamPOJO.getDate(), hipExamPOJO.getRightRom2());

            dateValues1.add(dateValue1);
            dateValues2.add(dateValue2);

        }

        GraphResultPOJO graphResultPOJO1 = graphResultPOJO("Supination", dateValues1);
        GraphResultPOJO graphResultPOJO2 = graphResultPOJO("Pronation", dateValues2);

        List<GraphResultPOJO> graphResultPOJOS = new ArrayList<>();
        graphResultPOJOS.add(graphResultPOJO1);
        graphResultPOJOS.add(graphResultPOJO2);


        graphPOJO.setGraphResultPOJOS(graphResultPOJOS);

        return graphPOJO;
    }

    public static GraphPOJO getWristRightDateValueList(List<HipExamPOJO> hipExamPOJOS) {
        Collections.reverse(hipExamPOJOS);
        GraphPOJO graphPOJO = new GraphPOJO();
        List<DateValue> dateValues1 = new ArrayList<>();
        List<DateValue> dateValues2 = new ArrayList<>();
        List<DateValue> dateValues3 = new ArrayList<>();
        List<DateValue> dateValues4 = new ArrayList<>();

        for (HipExamPOJO hipExamPOJO : hipExamPOJOS) {
            DateValue dateValue1 = new DateValue(hipExamPOJO.getDate(), hipExamPOJO.getRightRom1());
            DateValue dateValue2 = new DateValue(hipExamPOJO.getDate(), hipExamPOJO.getRightRom2());
            DateValue dateValue3 = new DateValue(hipExamPOJO.getDate(), hipExamPOJO.getRightRom3());
            DateValue dateValue4 = new DateValue(hipExamPOJO.getDate(), hipExamPOJO.getRightRom4());

            dateValues1.add(dateValue1);
            dateValues2.add(dateValue2);
            dateValues3.add(dateValue3);
            dateValues4.add(dateValue4);
        }

        GraphResultPOJO graphResultPOJO1 = graphResultPOJO("Flexion", dateValues1);
        GraphResultPOJO graphResultPOJO2 = graphResultPOJO("Extension", dateValues2);
        GraphResultPOJO graphResultPOJO3 = graphResultPOJO("Radial Deviation", dateValues3);
        GraphResultPOJO graphResultPOJO4 = graphResultPOJO("Ulnar Deviation", dateValues4);

        List<GraphResultPOJO> graphResultPOJOS = new ArrayList<>();
        graphResultPOJOS.add(graphResultPOJO1);
        graphResultPOJOS.add(graphResultPOJO2);
        graphResultPOJOS.add(graphResultPOJO3);
        graphResultPOJOS.add(graphResultPOJO4);

        graphPOJO.setGraphResultPOJOS(graphResultPOJOS);

        return graphPOJO;
    }

    public static GraphPOJO getFingetRightDateValueList(List<HipExamPOJO> hipExamPOJOS) {
        Collections.reverse(hipExamPOJOS);
        GraphPOJO graphPOJO = new GraphPOJO();
        List<DateValue> dateValues1 = new ArrayList<>();
        List<DateValue> dateValues2 = new ArrayList<>();
        List<DateValue> dateValues3 = new ArrayList<>();
        List<DateValue> dateValues4 = new ArrayList<>();

        for (HipExamPOJO hipExamPOJO : hipExamPOJOS) {
            DateValue dateValue1 = new DateValue(hipExamPOJO.getDate(), hipExamPOJO.getRightRom1());
            DateValue dateValue2 = new DateValue(hipExamPOJO.getDate(), hipExamPOJO.getRightRom2());
            DateValue dateValue3 = new DateValue(hipExamPOJO.getDate(), hipExamPOJO.getRightRom3());
            DateValue dateValue4 = new DateValue(hipExamPOJO.getDate(), hipExamPOJO.getRightRom4());

            dateValues1.add(dateValue1);
            dateValues2.add(dateValue2);
            dateValues3.add(dateValue3);
            dateValues4.add(dateValue4);
        }

        GraphResultPOJO graphResultPOJO1 = graphResultPOJO("Flexion", dateValues1);
        GraphResultPOJO graphResultPOJO2 = graphResultPOJO("Extension", dateValues2);
        GraphResultPOJO graphResultPOJO3 = graphResultPOJO("Abduction", dateValues3);
        GraphResultPOJO graphResultPOJO4 = graphResultPOJO("Adduction", dateValues4);

        List<GraphResultPOJO> graphResultPOJOS = new ArrayList<>();
        graphResultPOJOS.add(graphResultPOJO1);
        graphResultPOJOS.add(graphResultPOJO2);
        graphResultPOJOS.add(graphResultPOJO3);
        graphResultPOJOS.add(graphResultPOJO4);

        graphPOJO.setGraphResultPOJOS(graphResultPOJOS);

        return graphPOJO;
    }

    public static GraphPOJO getHipLeftDateValueList(List<HipExamPOJO> hipExamPOJOS) {
        Collections.reverse(hipExamPOJOS);
        GraphPOJO graphPOJO = new GraphPOJO();
        List<DateValue> dateValues1 = new ArrayList<>();
        List<DateValue> dateValues2 = new ArrayList<>();
        List<DateValue> dateValues3 = new ArrayList<>();
        List<DateValue> dateValues4 = new ArrayList<>();
        List<DateValue> dateValues5 = new ArrayList<>();
        List<DateValue> dateValues6 = new ArrayList<>();

        for (HipExamPOJO hipExamPOJO : hipExamPOJOS) {
            DateValue dateValue1 = new DateValue(hipExamPOJO.getDate(), hipExamPOJO.getLeftRom1());
            DateValue dateValue2 = new DateValue(hipExamPOJO.getDate(), hipExamPOJO.getLeftRom2());
            DateValue dateValue3 = new DateValue(hipExamPOJO.getDate(), hipExamPOJO.getLeftRom3());
            DateValue dateValue4 = new DateValue(hipExamPOJO.getDate(), hipExamPOJO.getLeftRom4());
            DateValue dateValue5 = new DateValue(hipExamPOJO.getDate(), hipExamPOJO.getLeftRom5());
            DateValue dateValue6 = new DateValue(hipExamPOJO.getDate(), hipExamPOJO.getLeftRom6());

            dateValues1.add(dateValue1);
            dateValues2.add(dateValue2);
            dateValues3.add(dateValue3);
            dateValues4.add(dateValue4);
            dateValues5.add(dateValue5);
            dateValues6.add(dateValue6);
        }

        GraphResultPOJO graphResultPOJO1 = graphResultPOJO("Flexion", dateValues1);
        GraphResultPOJO graphResultPOJO2 = graphResultPOJO("Extension", dateValues2);
        GraphResultPOJO graphResultPOJO3 = graphResultPOJO("External Rotation", dateValues3);
        GraphResultPOJO graphResultPOJO4 = graphResultPOJO("Internal Rotation", dateValues4);
        GraphResultPOJO graphResultPOJO5 = graphResultPOJO("Abduction", dateValues5);
        GraphResultPOJO graphResultPOJO6 = graphResultPOJO("Adduction", dateValues6);

        List<GraphResultPOJO> graphResultPOJOS = new ArrayList<>();
        graphResultPOJOS.add(graphResultPOJO1);
        graphResultPOJOS.add(graphResultPOJO2);
        graphResultPOJOS.add(graphResultPOJO3);
        graphResultPOJOS.add(graphResultPOJO4);
        graphResultPOJOS.add(graphResultPOJO5);
        graphResultPOJOS.add(graphResultPOJO6);

        graphPOJO.setGraphResultPOJOS(graphResultPOJOS);

        return graphPOJO;
    }

    public static GraphResultPOJO graphResultPOJO(String line_name, List<DateValue> dateValues) {
        GraphResultPOJO graphResultPOJO = new GraphResultPOJO();
        graphResultPOJO.setLine_name(line_name);
        graphResultPOJO.setDateValues(dateValues);

        return graphResultPOJO;
    }

    public static GraphPOJO getKneeLeftDateValueList(List<HipExamPOJO> hipExamPOJOS) {
        Collections.reverse(hipExamPOJOS);
        GraphPOJO graphPOJO = new GraphPOJO();
        List<DateValue> dateValues1 = new ArrayList<>();
        List<DateValue> dateValues2 = new ArrayList<>();


        for (HipExamPOJO hipExamPOJO: hipExamPOJOS) {
            DateValue dateValue1 = new DateValue(hipExamPOJO.getDate(), hipExamPOJO.getLeftRom1());
            DateValue dateValue2 = new DateValue(hipExamPOJO.getDate(), hipExamPOJO.getLeftRom2());

            dateValues1.add(dateValue1);
            dateValues2.add(dateValue2);

        }

        GraphResultPOJO graphResultPOJO1 = graphResultPOJO("Flexion", dateValues1);
        GraphResultPOJO graphResultPOJO2 = graphResultPOJO("Extension", dateValues2);

        List<GraphResultPOJO> graphResultPOJOS = new ArrayList<>();
        graphResultPOJOS.add(graphResultPOJO1);
        graphResultPOJOS.add(graphResultPOJO2);


        graphPOJO.setGraphResultPOJOS(graphResultPOJOS);

        return graphPOJO;
    }

    public static GraphPOJO getAnkleLeftDateValueList(List<HipExamPOJO> hipExamPOJOS) {
        Collections.reverse(hipExamPOJOS);
        GraphPOJO graphPOJO = new GraphPOJO();
        List<DateValue> dateValues1 = new ArrayList<>();
        List<DateValue> dateValues2 = new ArrayList<>();
        List<DateValue> dateValues3 = new ArrayList<>();
        List<DateValue> dateValues4 = new ArrayList<>();


        for (HipExamPOJO hipExamPOJO: hipExamPOJOS) {
            DateValue dateValue1 = new DateValue(hipExamPOJO.getDate(), hipExamPOJO.getLeftRom1());
            DateValue dateValue2 = new DateValue(hipExamPOJO.getDate(), hipExamPOJO.getLeftRom2());
            DateValue dateValue3 = new DateValue(hipExamPOJO.getDate(), hipExamPOJO.getLeftRom3());
            DateValue dateValue4 = new DateValue(hipExamPOJO.getDate(), hipExamPOJO.getLeftRom4());

            dateValues1.add(dateValue1);
            dateValues2.add(dateValue2);
            dateValues3.add(dateValue3);
            dateValues4.add(dateValue4);

        }

        GraphResultPOJO graphResultPOJO1 = graphResultPOJO("Plantar Flexion", dateValues1);
        GraphResultPOJO graphResultPOJO2 = graphResultPOJO("Dorsi Flexion", dateValues2);
        GraphResultPOJO graphResultPOJO3 = graphResultPOJO("Eversion", dateValues3);
        GraphResultPOJO graphResultPOJO4 = graphResultPOJO("Iversion", dateValues4);

        List<GraphResultPOJO> graphResultPOJOS = new ArrayList<>();
        graphResultPOJOS.add(graphResultPOJO1);
        graphResultPOJOS.add(graphResultPOJO2);
        graphResultPOJOS.add(graphResultPOJO3);
        graphResultPOJOS.add(graphResultPOJO4);


        graphPOJO.setGraphResultPOJOS(graphResultPOJOS);

        return graphPOJO;
    }

    public static GraphPOJO getToesLeftDateValueList(List<HipExamPOJO> hipExamPOJOS) {
        Collections.reverse(hipExamPOJOS);
        GraphPOJO graphPOJO = new GraphPOJO();
        List<DateValue> dateValues1 = new ArrayList<>();
        List<DateValue> dateValues2 = new ArrayList<>();


        for (HipExamPOJO hipExamPOJO: hipExamPOJOS) {
            DateValue dateValue1 = new DateValue(hipExamPOJO.getDate(), hipExamPOJO.getLeftRom1());
            DateValue dateValue2 = new DateValue(hipExamPOJO.getDate(), hipExamPOJO.getLeftRom2());

            dateValues1.add(dateValue1);
            dateValues2.add(dateValue2);

        }

        GraphResultPOJO graphResultPOJO1 = graphResultPOJO("Flexion", dateValues1);
        GraphResultPOJO graphResultPOJO2 = graphResultPOJO("Extension", dateValues2);

        List<GraphResultPOJO> graphResultPOJOS = new ArrayList<>();
        graphResultPOJOS.add(graphResultPOJO1);
        graphResultPOJOS.add(graphResultPOJO2);


        graphPOJO.setGraphResultPOJOS(graphResultPOJOS);

        return graphPOJO;
    }

    public static GraphPOJO getShoulderLeftDateValueList(List<HipExamPOJO> hipExamPOJOS) {
        Collections.reverse(hipExamPOJOS);
        GraphPOJO graphPOJO = new GraphPOJO();
        List<DateValue> dateValues1 = new ArrayList<>();
        List<DateValue> dateValues2 = new ArrayList<>();
        List<DateValue> dateValues3 = new ArrayList<>();
        List<DateValue> dateValues4 = new ArrayList<>();
        List<DateValue> dateValues5 = new ArrayList<>();
        List<DateValue> dateValues6 = new ArrayList<>();

        for (HipExamPOJO hipExamPOJO : hipExamPOJOS) {
            DateValue dateValue1 = new DateValue(hipExamPOJO.getDate(), hipExamPOJO.getLeftRom1());
            DateValue dateValue2 = new DateValue(hipExamPOJO.getDate(), hipExamPOJO.getLeftRom2());
            DateValue dateValue3 = new DateValue(hipExamPOJO.getDate(), hipExamPOJO.getLeftRom3());
            DateValue dateValue4 = new DateValue(hipExamPOJO.getDate(), hipExamPOJO.getLeftRom4());
            DateValue dateValue5 = new DateValue(hipExamPOJO.getDate(), hipExamPOJO.getLeftRom5());
            DateValue dateValue6 = new DateValue(hipExamPOJO.getDate(), hipExamPOJO.getLeftRom6());

            dateValues1.add(dateValue1);
            dateValues2.add(dateValue2);
            dateValues3.add(dateValue3);
            dateValues4.add(dateValue4);
            dateValues5.add(dateValue5);
            dateValues6.add(dateValue6);
        }

        GraphResultPOJO graphResultPOJO1 = graphResultPOJO("Flexion", dateValues1);
        GraphResultPOJO graphResultPOJO2 = graphResultPOJO("Extension", dateValues2);
        GraphResultPOJO graphResultPOJO3 = graphResultPOJO("External Rotation", dateValues3);
        GraphResultPOJO graphResultPOJO4 = graphResultPOJO("Internal Rotation", dateValues4);
        GraphResultPOJO graphResultPOJO5 = graphResultPOJO("Abduction", dateValues5);
        GraphResultPOJO graphResultPOJO6 = graphResultPOJO("Adduction", dateValues6);

        List<GraphResultPOJO> graphResultPOJOS = new ArrayList<>();
        graphResultPOJOS.add(graphResultPOJO1);
        graphResultPOJOS.add(graphResultPOJO2);
        graphResultPOJOS.add(graphResultPOJO3);
        graphResultPOJOS.add(graphResultPOJO4);
        graphResultPOJOS.add(graphResultPOJO5);
        graphResultPOJOS.add(graphResultPOJO6);

        graphPOJO.setGraphResultPOJOS(graphResultPOJOS);

        return graphPOJO;
    }

    public static GraphPOJO getForearmLeftDateValueList(List<HipExamPOJO> hipExamPOJOS) {
        Collections.reverse(hipExamPOJOS);
        GraphPOJO graphPOJO = new GraphPOJO();
        List<DateValue> dateValues1 = new ArrayList<>();
        List<DateValue> dateValues2 = new ArrayList<>();


        for (HipExamPOJO hipExamPOJO: hipExamPOJOS) {
            DateValue dateValue1 = new DateValue(hipExamPOJO.getDate(), hipExamPOJO.getLeftRom1());
            DateValue dateValue2 = new DateValue(hipExamPOJO.getDate(), hipExamPOJO.getLeftRom2());

            dateValues1.add(dateValue1);
            dateValues2.add(dateValue2);

        }

        GraphResultPOJO graphResultPOJO1 = graphResultPOJO("Supination", dateValues1);
        GraphResultPOJO graphResultPOJO2 = graphResultPOJO("Pronation", dateValues2);

        List<GraphResultPOJO> graphResultPOJOS = new ArrayList<>();
        graphResultPOJOS.add(graphResultPOJO1);
        graphResultPOJOS.add(graphResultPOJO2);


        graphPOJO.setGraphResultPOJOS(graphResultPOJOS);

        return graphPOJO;
    }

    public static GraphPOJO getWristLeftDateValueList(List<HipExamPOJO> hipExamPOJOS) {
        Collections.reverse(hipExamPOJOS);
        GraphPOJO graphPOJO = new GraphPOJO();
        List<DateValue> dateValues1 = new ArrayList<>();
        List<DateValue> dateValues2 = new ArrayList<>();
        List<DateValue> dateValues3 = new ArrayList<>();
        List<DateValue> dateValues4 = new ArrayList<>();

        for (HipExamPOJO hipExamPOJO : hipExamPOJOS) {
            DateValue dateValue1 = new DateValue(hipExamPOJO.getDate(), hipExamPOJO.getLeftRom1());
            DateValue dateValue2 = new DateValue(hipExamPOJO.getDate(), hipExamPOJO.getLeftRom2());
            DateValue dateValue3 = new DateValue(hipExamPOJO.getDate(), hipExamPOJO.getLeftRom3());
            DateValue dateValue4 = new DateValue(hipExamPOJO.getDate(), hipExamPOJO.getLeftRom4());

            dateValues1.add(dateValue1);
            dateValues2.add(dateValue2);
            dateValues3.add(dateValue3);
            dateValues4.add(dateValue4);
        }

        GraphResultPOJO graphResultPOJO1 = graphResultPOJO("Flexion", dateValues1);
        GraphResultPOJO graphResultPOJO2 = graphResultPOJO("Extension", dateValues2);
        GraphResultPOJO graphResultPOJO3 = graphResultPOJO("Radial Deviation", dateValues3);
        GraphResultPOJO graphResultPOJO4 = graphResultPOJO("Ulnar Deviation", dateValues4);

        List<GraphResultPOJO> graphResultPOJOS = new ArrayList<>();
        graphResultPOJOS.add(graphResultPOJO1);
        graphResultPOJOS.add(graphResultPOJO2);
        graphResultPOJOS.add(graphResultPOJO3);
        graphResultPOJOS.add(graphResultPOJO4);

        graphPOJO.setGraphResultPOJOS(graphResultPOJOS);

        return graphPOJO;
    }

    public static GraphPOJO getFingetLeftDateValueList(List<HipExamPOJO> hipExamPOJOS) {
        Collections.reverse(hipExamPOJOS);
        GraphPOJO graphPOJO = new GraphPOJO();
        List<DateValue> dateValues1 = new ArrayList<>();
        List<DateValue> dateValues2 = new ArrayList<>();
        List<DateValue> dateValues3 = new ArrayList<>();
        List<DateValue> dateValues4 = new ArrayList<>();

        for (HipExamPOJO hipExamPOJO : hipExamPOJOS) {
            DateValue dateValue1 = new DateValue(hipExamPOJO.getDate(), hipExamPOJO.getLeftRom1());
            DateValue dateValue2 = new DateValue(hipExamPOJO.getDate(), hipExamPOJO.getLeftRom2());
            DateValue dateValue3 = new DateValue(hipExamPOJO.getDate(), hipExamPOJO.getLeftRom3());
            DateValue dateValue4 = new DateValue(hipExamPOJO.getDate(), hipExamPOJO.getLeftRom4());

            dateValues1.add(dateValue1);
            dateValues2.add(dateValue2);
            dateValues3.add(dateValue3);
            dateValues4.add(dateValue4);
        }

        GraphResultPOJO graphResultPOJO1 = graphResultPOJO("Flexion", dateValues1);
        GraphResultPOJO graphResultPOJO2 = graphResultPOJO("Extension", dateValues2);
        GraphResultPOJO graphResultPOJO3 = graphResultPOJO("Abduction", dateValues3);
        GraphResultPOJO graphResultPOJO4 = graphResultPOJO("Adduction", dateValues4);

        List<GraphResultPOJO> graphResultPOJOS = new ArrayList<>();
        graphResultPOJOS.add(graphResultPOJO1);
        graphResultPOJOS.add(graphResultPOJO2);
        graphResultPOJOS.add(graphResultPOJO3);
        graphResultPOJOS.add(graphResultPOJO4);

        graphPOJO.setGraphResultPOJOS(graphResultPOJOS);

        return graphPOJO;
    }



}
