package com.example.myapplication1.functional;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

public class Intellect {
    class Steps
    {
        public List<Integer> CurArry;
        public int Distance;
        public int StepCount;
        public Steps prevStep;
    }

//    List<Integer> intArry = new ArrayList<Integer>() { 0, 1, 2, 3, 4, 5, 6, 7, -1 };
//
//    List<Steps> listWhiteSteps = new ArrayList<Steps>();
//    Dictionary<String, Steps> AllSteps = new Dictionary<String, Steps>();
//    List<Steps> listCurretSteps = new ArrayList<Steps>();
//
//    private void Compute()
//    {
//        listWhiteSteps.clear();
//        AllSteps.clear();
//        Steps topStep = new Steps();
//        topStep.CurArry = intArry;
//        topStep.Distance = MathDistance(topStep.CurArry);
//        topStep.StepCount = 0;
//        topStep.prevStep = null;
//        listWhiteSteps.add(topStep);
//        boolean finish = false;
//        allMinStep = topStep;
//        while (!finish)
//        {
//            finish = ComputeSteps(allMinStep);
//        }
//    }
//    Steps allMinStep = null;
//    private boolean ComputeSteps(Steps step)
//    {
//        if (step.Distance == 0)
//        {
//            //已经找到最终结果。
//            listCurretSteps.add(step);
//            Steps prevStep = step.prevStep;
//            while (prevStep != null)
//            {
//                listCurretSteps.insert(0, prevStep);
//                prevStep = prevStep.prevStep;
//            }
//            listCurretSteps.RemoveAt(0);
//            return true;
//        }
//        //计算所有可能的下一步
//        List<Steps> nextSteps = MathNextSteps(step);
//        allMinStep = FindMinStep(nextSteps);
//        if (allMinStep == null)
//        {
//            tbAlert.Text = "未找到合适的路径！";
//            return true;
//        }
//        return false;
//    }
//
//    private Steps FindMinStep(List<Steps> nextSteps)
//    {
//        int mindis = -1;
//        Steps minStep = null;
//        if (nextSteps.Count > 0)
//        {
//            foreach (Steps step in nextSteps)
//            {
//                if (mindis == -1)
//                {
//                    mindis = step.Distance + step.StepCount;
//                    minStep = step;
//                }
//                if (mindis > step.Distance + step.StepCount)
//                {
//                    mindis = step.Distance + step.StepCount;
//                    minStep = step;
//                }
//            }
//        }
//        else
//        {
//            foreach (Steps step in listWhiteSteps)
//            {
//                if (mindis == -1)
//                {
//                    mindis = step.Distance + step.StepCount;
//                    minStep = step;
//                }
//                if (mindis > step.Distance + step.StepCount)
//                {
//                    mindis = step.Distance + step.StepCount;
//                    minStep = step;
//                }
//            }
//        }
//        return minStep;
//    }
//
//    private List<Steps> MathNextSteps(Steps prevStep)
//    {
//        List<int> list = prevStep.CurArry;
//        List<Steps> nextSteps = new List<Steps>();
//        for (int i = 0; i < list.Count; i++)
//        {
//            if (list[i] == -1)
//            {
//                List<int> changeIndex = new List<int>();
//                //上
//                if (i > 2)
//                {
//                    changeIndex.Add(i - 3);
//                }
//                //左
//                if (i % 3 != 0)
//                {
//                    changeIndex.Add(i - 1);
//                }
//                //右
//                if ((i + 1) % 3 != 0)
//                {
//                    changeIndex.Add(i + 1);
//                }
//                //下
//                if (i < 6)
//                {
//                    changeIndex.Add(i + 3);
//                }
//                for (int j = 0; j < changeIndex.Count; j++)
//                {
//                    List<int> childList = new List<int>(list);
//                    int number = childList.get(changeIndex.get(j));
//                    childList.set(changeIndex.get(j), -1);
//                    childList.set(i, number);
//                    Steps step = new Steps();
//                    step.CurArry = childList;
//                    step.prevStep = prevStep;
//                    step.StepCount = prevStep.StepCount + 1;
//                    step.Distance = MathDistance(childList);
//                    string checkStr = StepToString(step);
//                    if (!AllSteps.ContainsKey(checkStr))
//                    {
//                        AllSteps.Add(checkStr, step);
//                        listWhiteSteps.Add(step);
//                        if (step.Distance < prevStep.Distance)
//                        {
//                            nextSteps.Add(step);
//                        }
//                    }
//                    else
//                    {
//                        if (step.StepCount < AllSteps[checkStr].StepCount)
//                        {
//                            Steps changeStep = AllSteps[checkStr];
//                            changeStep.StepCount = step.StepCount;
//                            changeStep.prevStep = prevStep;
//                            if (!listWhiteSteps.Contains(changeStep))
//                            {
//                                listWhiteSteps.Add(changeStep);
//                            }
//                            if (step.Distance < prevStep.Distance)
//                            {
//                                nextSteps.Add(step);
//                            }
//                        }
//                    }
//                }
//                break;
//            }
//        }
//        listWhiteSteps.Remove(prevStep);
//        return nextSteps;
//    }
//
//    private String StepToString(Steps step)
//    {
//        String str = "";
//        for (int i = 0; i < step.CurArry.Count; i++)
//        {
//            str += step.CurArry.get(i).ToString();
//        }
//        return str;
//    }
//
//
//    private int MathDistance(List<Integer> list)
//    {
//        int distance = 0;
//        for (int i = 0; i < list.Count; i++)
//        {
//            int curPos = i;
//            int tarPos = list.get(i);
//            if (tarPos == -1)
//            {
//                tarPos = 8;
//            }
//            int steps = tarPos - curPos;
//            if (steps < 0)
//            {
//                steps = -steps;
//            }
//            steps = steps / 3 + steps % 3;
//            distance += steps;
//        }
//        return distance;
//    }
}
