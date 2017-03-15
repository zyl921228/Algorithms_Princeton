import edu.princeton.cs.algs4.WeightedQuickUnionUF; 
public class Percolation {
	private int _n;
	private boolean[] sitesOpen;
	private WeightedQuickUnionUF uf;
	private WeightedQuickUnionUF uf_backwash;
	private int numOfOpenSites=0;
	public Percolation(int n){
		if(n<=0)
		{
			throw new IllegalArgumentException("n of n_by_n should be positive");
		}
		_n=n;
		numOfOpenSites=0;
		uf=new WeightedQuickUnionUF(_n*_n+2);//+2 virtual top and bottom sites
		sitesOpen=new boolean[_n*_n+2];
		sitesOpen[0]=true;
		sitesOpen[_n*_n+1]=true;
		for(int i=1;i<=_n;i++)
			uf.union(0, i);
		for(int i=_n*_n-_n+1;i<=_n*_n;i++)
			uf.union(_n*_n+1, i);
		uf_backwash=new WeightedQuickUnionUF(_n*_n+1);
		for(int i=1;i<=_n;i++)
			uf_backwash.union(0, i);
	}
	public void open(int row, int col)
	{
		if(row>_n||col>_n||row<1||col<1)
		{
			throw new IndexOutOfBoundsException("row or col index out of bounds");
		}
		int pos=(row-1)*_n+col;
		if(!sitesOpen[pos])
		{
		sitesOpen[pos]=true;
		numOfOpenSites++;
		}
		if(col>1&&sitesOpen[pos-1])//left
		{
			uf.union(pos, pos-1);
			uf_backwash.union(pos, pos-1);
		}
		if(row>1&&sitesOpen[pos-_n])//top
		{
			uf.union(pos, pos-_n);
			uf_backwash.union(pos, pos-_n);
		}
		if(col<_n&&sitesOpen[pos+1])//right
		{
			uf.union(pos, pos+1);
			uf_backwash.union(pos, pos+1);
		}
		if(row<_n&&sitesOpen[pos+_n])//bottom
		{
			uf.union(pos, pos+_n);
			uf_backwash.union(pos, pos+_n);
		}
	}
	public boolean isOpen(int row, int col){
		if(row>_n||col>_n||row<1||col<1)
		{
			throw new IndexOutOfBoundsException("row or col index out of bounds");
		}
		int pos=(row-1)*_n+col;
		return sitesOpen[pos];
	}

	public boolean isFull(int row, int col){
		if(row>_n||col>_n||row<1||col<1)
		{
			throw new IndexOutOfBoundsException("row or col index out of bounds");
		}
		int pos=(row-1)*_n+col;
		return (sitesOpen[pos]&&uf_backwash.connected(pos, 0));
	}
	public int numberOfOpenSites()
	{
		return numOfOpenSites;
	}
	public boolean percolates(){
		if(_n==1&&!sitesOpen[_n*_n])
			return false;
		return (uf.connected(_n*_n+1, 0));
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
