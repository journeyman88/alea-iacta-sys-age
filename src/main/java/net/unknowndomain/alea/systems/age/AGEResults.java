/*
 * Copyright 2020 Marco Bignami.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.unknowndomain.alea.systems.age;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import net.unknowndomain.alea.messages.MsgBuilder;
import net.unknowndomain.alea.messages.MsgStyle;
import net.unknowndomain.alea.random.SingleResult;
import net.unknowndomain.alea.roll.LocalizedResult;

/**
 *
 * @author journeyman
 */
public class AGEResults extends LocalizedResult
{
    private final static String BUNDLE_NAME = "net.unknowndomain.alea.systems.age.RpgSystemBundle";
    
    private final List<SingleResult<Integer>> results;
    private final SingleResult<Integer> dragonDice;
    private int total = 0;
    private int stuntPoints = 0;
    
    public AGEResults(SingleResult<Integer> ... actionResults)
    {
        List<SingleResult<Integer>> tmp = new ArrayList<>();
        tmp.addAll(Arrays.asList(actionResults));
        this.results = Collections.unmodifiableList(tmp);
        this.dragonDice = results.get(0);
    }

    public List<SingleResult<Integer>> getResults()
    {
        return results;
    }

    @Override
    protected void formatResults(MsgBuilder messageBuilder, boolean verbose, int indentValue)
    {
        messageBuilder.append(translate("age.results.total", total));
        messageBuilder.append(" | ").append(translate("age.results.dragonDice", dragonDice.getValue())).appendNewLine();
        if (stuntPoints > 0)
        {
            messageBuilder.append(translate("age.results.stuntPoints", stuntPoints)).appendNewLine();
        }
        if (verbose)
        {
            messageBuilder.append("Roll ID: ").append(getUuid()).appendNewLine();
            messageBuilder.append(translate("age.results.diceResults")).append(" [ ");
            boolean first = true;
            for (SingleResult<Integer> t : getResults())
            {
                if (first)
                {
                    messageBuilder.append("( ", MsgStyle.BOLD).append(t.getLabel(), MsgStyle.BOLD).append(" => ", MsgStyle.BOLD);
                    messageBuilder.append(t.getValue(), MsgStyle.BOLD).append(") ", MsgStyle.BOLD);
                    first = false;
                }
                else
                {
                    messageBuilder.append("( ").append(t.getLabel()).append(" => ");
                    messageBuilder.append(t.getValue()).append(") ");
                }
                messageBuilder.append(" ");
            }
            messageBuilder.append("]").appendNewLine();
        }
    }

    public SingleResult<Integer> getDragonDice()
    {
        return dragonDice;
    }

    public int getTotal()
    {
        return total;
    }

    public void setTotal(int total)
    {
        this.total = total;
    }

    public int getStuntPoints()
    {
        return stuntPoints;
    }

    public void setStuntPoints(int stuntPoints)
    {
        this.stuntPoints = stuntPoints;
    }

    @Override
    protected String getBundleName()
    {
        return BUNDLE_NAME;
    }

}
