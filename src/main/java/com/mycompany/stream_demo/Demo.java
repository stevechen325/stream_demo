package com.mycompany.stream_demo;

import com.google.common.collect.Lists;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 * @author Steve
 */
public class Demo {

    public static void main(String[] args) {

        List<Color> list = genList(50);
        //filter 過濾出綠色
        List<Color> green = list.stream().filter(each -> "綠色".equals(each.getName())).collect(Collectors.toList());
        System.out.println("green = " + green);
        //map 調整透明度
        List<Integer> alpha = list.stream().map(each -> each.getAlpha()*2).collect(Collectors.toList());
        System.out.println("alpha = " + alpha);
        //anyMatch
        boolean flag = list.stream().anyMatch(each -> "黑色".equals(each.getName()));
        System.out.println("flag = " + flag);
        //forEach
        list.stream().forEach(System.out::println);
        //reduce
        Integer sum = list.stream().map(Color::getAlpha).reduce(0, (a,b) -> a+b);
        System.out.println("sum = " + sum);

        System.out.println("===============================================================================================================");

        List<Color> list2 = genList(60);
        List<Color> list3 = genList(70);
        List<Color> list4 = genList(80);
        List<Color> list5 = genList(90);

        //若想收集所有 list 中的紅色，該怎麼取
        List<List<Color>> all = Lists.newArrayList(list, list2, list3, list4, list5);
        List<Color> reds = all.stream().flatMap(each -> each.stream()).filter(each -> "紅色".equals(each.getName())).collect(Collectors.toList());
        System.out.println("reds = " + reds);
        //toMap
        Map<String, Integer> alphaByName = list.stream().collect(Collectors.toMap(Color::getName, Color::getAlpha));
        System.out.println("alphaByName = " + alphaByName);
        //groupingBy 若想將所有 list 改以同顏色放一起呢
        Map<String, List<Color>> listByColorName = all.stream().flatMap(each -> each.stream()).collect(Collectors.groupingBy(Color::getName));
        System.out.println("listByColorName = " + listByColorName);
        //groupingBy 用 name 與 alpha 當 key
        Map<String, Map<Integer, List<Color>>> listByNameAndAlpha = all.stream().flatMap(each -> each.stream()).collect(Collectors.groupingBy(Color::getName, Collectors.groupingBy(Color::getAlpha)));
        System.out.println("listByNameAndAlpha = " + listByNameAndAlpha);
        //groupingBy 進階 若想從所有 list 找出每種顏色的最大透明度
        Map<String, Optional<Color>> maxAlphaByColorName = all.stream().flatMap(each -> each.stream()).collect(Collectors.groupingBy(Color::getName, Collectors.maxBy(Comparator.comparing(Color::getAlpha))));
        List<Color> maxColor = maxAlphaByColorName.entrySet().stream().map(each -> each.getValue()).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList());
        System.out.println("maxColor = " + maxColor);
    }

    private static List<Color> genList(Integer alpha) {
        List<Color> result = Lists.newArrayList();
        result.add(new Color("紅色", alpha));
        result.add(new Color("橙色", alpha));
        result.add(new Color("黃色", alpha));
        result.add(new Color("綠色", alpha));
        result.add(new Color("藍色", alpha));
        result.add(new Color("靛色", alpha));
        result.add(new Color("紫色", alpha));
        return result;
    }
}
