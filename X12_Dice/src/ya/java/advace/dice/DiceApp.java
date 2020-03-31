package ya.java.advace.dice;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiFunction;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DiceApp {
	public static final int Number_Of_Dice_Faces = 6;
	public static final int Number_Of_Throws= 10000;
	public static final int Max_Sum_Value= 12;
	public static final int Mix_Sum_Value= 2;

	public static void main(String[] args) {
		
		//Supplier to give a value between and with 1 and 6.
		IntSupplier diceValue = ()-> {return (int) ((Math.random()*10)%Number_Of_Dice_Faces +1 );};
		
		//simulates throwing a pair of Dices and return with the sum of the result.
		Supplier <Integer> sumOneThrow = () -> 
			{return Integer.sum(diceValue.getAsInt(), diceValue.getAsInt());};
		
		//Generate an infinite stream, stopping after specific number of throwing values.
		Stream<Integer> resultStream = Stream.generate(sumOneThrow).limit(Number_Of_Throws);
		
		//Create New map and fill it with all possible keys [2,12]and set all values to 0 to start counting. 
		Map <Integer, Integer> statisticResult = new TreeMap<>();
		for (int i = Mix_Sum_Value ; i <= Max_Sum_Value ; i++) {
			statisticResult.putIfAbsent(i, 0);
		}
		//Filling the map by adding 1 to the value of a specific key each time I got the key form the stream 
		//which is the sum of the two dices.		
		statisticResult= resultStream.collect(Collectors.toMap(k->k, v->v, (oldValue, newValue) -> oldValue + 1));
		//Calculating the Percentage.
		BiFunction <Integer, Integer, Float> Percentage = 
				(Integer total, Integer detcetedCases) ->
					{return (detcetedCases*100)/(float)total;};
		//Formating and printing the map.
		System.out.format("Throw Result\tNumber of Appearances\tPercentage chance%n\t\t   In %d Throw \t  Appearances%n----------------|-------------------|---------------%n",Number_Of_Throws);
		statisticResult.forEach((k,v) -> System.out.format("\t%d\t|\t  %d\t    |\t   %5.2f%% %n----------------|-------------------|---------------%n"
				,k,v, Percentage.apply(Number_Of_Throws, v)));
	}

}
