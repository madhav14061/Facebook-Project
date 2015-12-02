package Test2;

import java.util.ArrayList;
import java.util.HashMap;

public class Predictor 
{
	public Integer predictLikes(String s, String type)
	{
		double pLikes=0;
		String words[] = s.split(" ");
		double n = (double)words.length;
		double wordSum = 0;
		for(String w: words)
		{
			if(type.equals("status"))
			{
//				System.out.println(w);
				HashMap<String, ArrayList<Integer>> sHashMap = LikeCounter.statusHashMap;
//				System.out.println(sHashMap.containsKey("Shubham")+" " + sHashMap.containsKey("Kartik") + sHashMap.containsKey("To"));
				if(sHashMap.containsKey(w))
				{	
					
					ArrayList<Integer> likes = LikeCounter.statusHashMap.get(w);
					double lCount = (double) likes.size();
					double size = (double) likes.size();
					for(Integer l1:likes)
					{
						double l = (double)l1;
						double contributor = ((2*lCount)/((size+1)*size))*l;
//						System.out.println(lCount);
//						System.out.println(contributor);
						wordSum = wordSum + contributor;
					}
					
				}
			}
			else if(type.equals("photo"))
			{
				HashMap<String, ArrayList<Integer>> pHashMap = LikeCounter.photoHashMap;
				if(!pHashMap.containsKey(w))
					continue;
				ArrayList<Integer> likes = pHashMap.get(w);
				double lCount = (double) likes.size();
				double size = (double) likes.size();
				for(Integer l1:likes)
				{
					double l = (double)l1;
					double contributor = ((2*lCount)/((size+1)*size))*l;
					wordSum = wordSum + contributor;
				}
			}
			else if(type.equals("link"))
			{
				HashMap<String, ArrayList<Integer>> lHashMap = LikeCounter.linkHashMap;
				if(!lHashMap.containsKey(w))
					continue;
				ArrayList<Integer> likes = lHashMap.get(w);
				double lCount = (double) likes.size();
				double size = (double) likes.size();
				for(Integer l1:likes)
				{
					double l = (double)l1;
					double contributor = ((2*lCount)/((size+1)*size))*l;
					wordSum = wordSum + contributor;
				}
			}
			else if(type.equals("video"))
			{
				HashMap<String, ArrayList<Integer>> vHashMap = LikeCounter.videoHashMap;
				if(!vHashMap.containsKey(w))
					continue;
				ArrayList<Integer> likes = vHashMap.get(w);
				double lCount = (double) likes.size();
				double size = (double) likes.size();
				for(Integer l1:likes)
				{
					double l = (double)l1;
					double contributor = ((2*lCount)/((size+1)*size))*l;
					wordSum = wordSum + contributor;
				}
			}			
		}
		
		pLikes = wordSum/n;
		
		int toReturn = (int)pLikes;
		return toReturn;
	}
}
