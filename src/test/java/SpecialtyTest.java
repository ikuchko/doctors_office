import java.util.Arrays;

import org.junit.*;
import static org.junit.Assert.*;

public class SpecialtyTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void getAllDoctors_returnsAllDoctorsWithSpecialty() {
    Specialty specialty = new Specialty("Pediatrics");
    specialty.save();
    Doctor firstDoctor = new Doctor("John", "Doe", specialty.getId());
    firstDoctor.save();
    Doctor secondDoctor = new Doctor("Jane", "Roe", specialty.getId());
    secondDoctor.save();
    Doctor[] doctors = new Doctor[] {firstDoctor, secondDoctor};
    assertTrue(specialty.getAllDoctors().containsAll(Arrays.asList(doctors)));
  }

}
