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

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import net.unknowndomain.alea.random.SingleResult;
import net.unknowndomain.alea.random.dice.bag.D6;
import net.unknowndomain.alea.roll.GenericResult;
import net.unknowndomain.alea.roll.GenericRoll;

/**
 *
 * @author journeyman
 */
public class AGERoll implements GenericRoll
{
    
    
    
    private final int bonus;
    private final Set<AGEModifiers> mods;
    
    public AGERoll(Integer bonus, AGEModifiers ... mod)
    {
        this(bonus, Arrays.asList(mod));
    }
    
    public AGERoll(Integer bonus, Collection<AGEModifiers> mod)
    {
        this.mods = new HashSet<>();
        if (mod != null)
        {
            this.mods.addAll(mod);
        }
        this.bonus = bonus;
    }
    
    @Override
    public GenericResult getResult()
    {
        AGEResults results = buildResults(D6.INSTANCE.nextResult().get(), D6.INSTANCE.nextResult().get(), D6.INSTANCE.nextResult().get());
        results.setVerbose(mods.contains(AGEModifiers.VERBOSE));
        return results;
    }
    
    private AGEResults buildResults(SingleResult<Integer> res1, SingleResult<Integer> res2, SingleResult<Integer> res3)
    {
        AGEResults results = new AGEResults(res1, res2, res3);
        results.setTotal(res1.getValue() + res2.getValue() + res3.getValue() + bonus);
        if (
                (Objects.equals(res1.getValue(), res2.getValue())) || 
                (Objects.equals(res1.getValue(), res3.getValue())) || 
                (Objects.equals(res2.getValue(), res3.getValue())))
        {
            results.setStuntPoints(res1.getValue());
        }
        return results;
    }
}
