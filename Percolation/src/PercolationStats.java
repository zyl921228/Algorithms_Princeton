import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
public class PercolationStats {
	private double []thresholds;
	private int _trials;
	public PercolationStats(int n, int trials){
		if(n<=0||trials<=0)
			throw new IllegalArgumentException("n or trials should be positive");
		thresholds= new double [trials];
		_trials=trials;
		for(int i=0;i<trials;i++)
		{
			Percolation percolation=new Percolation(n);
			while(!percolation.percolates())
			{
				int tmpRow=StdRandom.uniform(1, n+1);
				int tmpCol=StdRandom.uniform(1, n+1);
				if(!percolation.isOpen(tmpRow, tmpCol))
				{
					percolation.open(tmpRow,tmpCol);
				}
			}
			thresholds[i]=(double)percolation.numberOfOpenSites()/(n*n);
		}

	}
	public double mean(){
		return StdStats.mean(thresholds);
	}
	public double stddev(){
		return StdStats.stddev(thresholds);
	}
	public double confidenceLo(){
		return (StdStats.mean(thresholds)-(1.96*StdStats.stddev(thresholds)/Math.sqrt(_trials)));
	}
	public double confidenceHi(){
		return (StdStats.mean(thresholds)+(1.96*StdStats.stddev(thresholds)/Math.sqrt(_trials)));
	}
	public static void main(String[] args){
		PercolationStats percolationStats=new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
		System.out.println("mean\t= "+percolationStats.mean());
		System.out.println("stddev\t= "+percolationStats.stddev());
		System.out.println("95% confidence interval\t= "+"["+percolationStats.confidenceLo()+", "+percolationStats.confidenceHi()+"]");
	}
}
