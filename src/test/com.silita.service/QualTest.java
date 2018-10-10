package com.silita.service;

import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Create by IntelliJ Idea 2018.1
 * Company: silita
 * Author: gemingyi
 * Date: 2018-10-09 8:50
 */
public class QualTest {

    public static void main(String[] args) {
        String group = "G1&G2&G4|G3";
        String G1 = "A1&A2&A3";
        String G2 = "B1|B2|B3";
        String G3 = "C1&C2&C3";
        String G4 = "D1|D2|D3";

        //按|分割成诺干块
        String[] blockQual = group.split("\\|");
        for (int i = 0; i < blockQual.length; i++) {
            String block = blockQual[i];
            //获取块
            List<String> temp = splitBlockQual(block);
//            System.out.println(temp);
            if (temp.size() > 0) {
                List<String> temp2 = null;
                Map<String, List> tempMap = new TreeMap();
                if (temp.size() > 1) {
                    //
                    for (int j = 0; j < temp.size(); j++) {
                        String singleGroup = temp.get(j);
                        if (singleGroup.equals("G1")) {
                            temp2 = splitSingleQual(G1);
                        } else if (singleGroup.equals("G2")) {
                            temp2 = splitSingleQual(G2);
                        } else if (singleGroup.equals("G3")) {
                            temp2 = splitSingleQual(G3);
                        } else if (singleGroup.equals("G4")) {
                            temp2 = splitSingleQual(G4);
                        }
                        tempMap.put("list" + j, temp2);
                    }

                    if(tempMap.size() == 2) {
                        merge(tempMap.get("list0"), tempMap.get("list1"));
                    } else if(tempMap.size() == 3) {
                        merge(tempMap.get("list0"), tempMap.get("list1"), tempMap.get("list2"));
                    }

                } else {
                    String singleGroup = temp.get(0);
                    if (singleGroup.equals("G1")) {
                        temp2 = splitSingleQual(G1);
                    } else if (singleGroup.equals("G2")) {
                        temp2 = splitSingleQual(G2);
                    } else if (singleGroup.equals("G3")) {
                        temp2 = splitSingleQual(G3);
                    } else if (singleGroup.equals("G4")) {
                        temp2 = splitSingleQual(G4);
                    }
                    System.out.println(temp2);
//                    tempMap.put("list", temp2);
                }
            }
        }

//        List<String> temp = splitSingleQual(G2);
//        System.out.println(temp);

        System.out.println("----------------");
        List list1 = Arrays.asList("A1A2A3");
        List list2 = Arrays.asList("B1", "B2", "B3");

//        merge(list1, list2);
    }

    /**
     * 拆分资质块
     *
     * @param blockQual
     * @return
     */
    public static List splitBlockQual(String blockQual) {
        List list = new ArrayList<String>(10);
        if (blockQual.contains("&")) {
            String[] combinationQual = blockQual.split("\\&");
            for (String tempQual : combinationQual) {
                list.add(tempQual);
            }
        } else {
            list.add(blockQual);
        }
        return list;
    }

    public static List splitSingleQual(String SingleGroup) {
        List list = new ArrayList<String>(10);
        if (!StringUtils.isEmpty(SingleGroup)) {
            if (SingleGroup.contains("&")) {
                StringBuilder sb = new StringBuilder();
                String[] temp = SingleGroup.split("\\&");
                for (int i = 0; i < temp.length; i++) {
                    sb.append(temp[i]);
                }
                list.add(sb.toString());
            } else {
                String[] temp = SingleGroup.split("\\|");
                for (int i = 0; i < temp.length; i++) {
                    list.add(temp[i]);
                }
            }
        }
        return list;
    }

    public static List merge(List... list) {
        List result = new ArrayList<String>(20);
        StringBuilder sb;
        if (list.length == 2) {
            for (int i = 0; i < list[0].size(); i++) {
                for (int j = 0; j < list[1].size(); j++) {
                    sb = new StringBuilder();
                    sb = sb.append(list[0].get(i)).append(list[1].get(j));
                    result.add(sb.toString());
                }
            }
            return result;
        }
        if (list.length == 3) {
            for (int i = 0; i < list[0].size(); i++) {
                for (int j = 0; j < list[1].size(); j++) {
                    for (int k = 0; k < list[2].size(); k++) {
                        sb = new StringBuilder();
                        sb = sb.append(list[0].get(i)).append(list[1].get(j)).append(list[2].get(k));
                        result.add(sb.toString());
                    }
                }
            }
            return result;
        }
        if(list.length == 4) {
            for (int i = 0; i < list[0].size(); i++) {
                for (int j = 0; j < list[1].size(); j++) {
                    for (int k = 0; k < list[2].size(); k++) {
                        for (int l = 0; l < list[3].size(); l++) {
                            sb = new StringBuilder();
                            sb = sb.append(list[0].get(i)).append(list[1].get(j)).append(list[2].get(k)).append(list[3].get(l));
                            result.add(sb.toString());
                        }
                    }
                }
            }
            return result;
        }
        if(list.length == 5) {
            for (int i = 0; i < list[0].size(); i++) {
                for (int j = 0; j < list[1].size(); j++) {
                    for (int k = 0; k < list[2].size(); k++) {
                        for (int l = 0; l < list[3].size(); l++) {
                            for (int m = 0; m < list[3].size(); m++) {
                                sb = new StringBuilder();
                                sb = sb.append(list[0].get(i)).append(list[1].get(j)).append(list[2].get(k)).append(list[3].get(l)).append(list[4].get(m));
                                result.add(sb.toString());
                            }
                        }
                    }
                }
            }
            return result;
        }
        System.out.println(result);
        return result;
    }
}
