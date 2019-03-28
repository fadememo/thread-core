/*
设计一个数组工具对象，例如ArrayUtil对象，该对象提供以下两个方法：
        	search(array,str): 传入指定的数组和指定的字符串，返回指定字符串所在的位置
            max(array): 传入指定的数组，返回该数组中的最大值

*/
function ArrayUtil(){
	//定义方法
	this.search = function(array,target){
			//遍历
			for(var i=0;i<array.length;i++){
				if(array[i]==target){
					return i;	
				}	
			}
			return -1; //找不到就是-1
		}
		
	this.max = function(array){
			//存储最大值
			var max = array[0];
			for(var i=1;i<array.length;i++){
				if(array[i]>max){
					max = array[i];
				}
			}
			return max;
		}

}
