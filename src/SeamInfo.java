import java.util.*;

public class SeamInfo {
  private Pixel corresponding;
  private double totalWeight;
  private SeamInfo upTo;

  public SeamInfo(Pixel corresponding) {
    this(corresponding, null);
  }

  public SeamInfo(Pixel corresponding, SeamInfo upTo) {
    this.corresponding = corresponding;
    this.upTo = upTo;
    this.setTotalWeight();
  }

  private void setTotalWeight() {
    if (this.upTo == null) {
      this.totalWeight = this.corresponding.getEnergy();
    } else {
      this.totalWeight = this.corresponding.getEnergy() + this.upTo.totalWeight;
    }
  }


  double getTotalWeight() {
    return totalWeight;
  }

  public List<Pixel> trace() {
    return this.traceAccumulator(new ArrayList<>());
  }

  private List<Pixel> traceAccumulator(List<Pixel> soFar) {
    soFar.add(corresponding);
    if(upTo == null) {
      return soFar;
    } else {
      return this.upTo.traceAccumulator(soFar);
    }
  }

  public boolean contains(Pixel p) {
    if(this.corresponding == p) {
      return true;
    } else if(this.upTo == null) {
      return false;
    } else {
      return this.upTo.contains(p);
    }
  }

  public SeamInfo endsWith(Pixel p) {
    if(this.corresponding == p) {
      return this;
    } else if(this.upTo == null) {
      throw new IllegalStateException();
    } else {
      return this.upTo.endsWith(p);
    }
  }
}
