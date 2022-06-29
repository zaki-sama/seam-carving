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
}
