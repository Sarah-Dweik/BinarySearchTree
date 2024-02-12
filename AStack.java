package finalTrial;

public class AStack<T> {
	private Object[] stack;
	int top = -1;
	
	public AStack(int size) {
		stack = new Object[size];
	}
	
	public boolean isEmpty() {
		if(top==-1) {
			return true;
		}
		return false;
	}
	public void push(T data) {
		top++;
		stack[top]= data;
	}
	
	public Object pop() {
		Object toDel = stack[top];
		top--;
		return toDel;
	}
	
	public Object peek() {
		Object first = stack[top];
		return first;
	}
	
	

}
