package Test2;

import java.util.ArrayList;
import java.util.HashMap;

public class Predictor 
{
	public Integer predictLikes(String s, String type)
	{
		System.out.println(s);
		Integer toReturn = null;
		int flag=0;
		double pLikes=0;
		String words[] = s.split(" ");
		double n = (double)words.length;
		double wordSum = 0;
		if(s.equals(null)){
			if(type.equals("status")){
				return LikeCounter.statusavg;
			}
			else if (type.equals("photo")){
				return LikeCounter.photoavg;
			}
			else if (type.equals("link")){
				return LikeCounter.linkavg;			
			}
			else if (type.equals("video")){
				return LikeCounter.videoavg;
			}
		}
		else{
		for(String w: words)
		{
			if(type.equals("status"))
			{
				if(LikeCounter.statusHashMap.containsKey(w))
				{flag=1;
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
			}
			else if(type.equals("photo"))
			{
				if(LikeCounter.photoHashMap.containsKey(w))
				{HashMap<String, ArrayList<Integer>> pHashMap = LikeCounter.photoHashMap;
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
			}
			else if(type.equals("link"))
			{
				if(LikeCounter.linkHashMap.containsKey(w)){
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
			}
			else if(type.equals("video"))
			{
				if(LikeCounter.videoHashMap.containsKey(w))
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
		}
		
		pLikes = wordSum/n;
		
		toReturn = (int)pLikes;
		
	}
		if(flag==1){
			
			return toReturn;
		}
		else{
			if(type.equals("status")){
				return LikeCounter.statusavg;
			}
			else if (type.equals("photo")){
				return LikeCounter.photoavg;
			}
			else if (type.equals("link")){
				return LikeCounter.linkavg;			
			}
			else if (type.equals("video")){
				return LikeCounter.videoavg;
			}
		}
		return toReturn;
	}
}
