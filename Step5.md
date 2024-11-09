Step 5 -------------------------------------------------------------------------------------------
==================================================================================================
How could the information your program provides after Step 4 be used by an FRC robot?
Instead of filtering pixels by RGB values, are there better ways to track objects?

An FRC robot can use the relative angles of objects on a game field to find the angle which it is 
facing. From there, it can position itself to a desired angle for scoring game pieces and navigating
around the field during the autonomous period.
Filtering by colour can lead to mixups with similarly-coloured objects and cameras can be influenced
by inconsistent lighting, so better alternative methods can include:
1. Using QR code-type images that cannot be confused (e.g. AprilTags).
2. In terms of sensing objects without the need for specifying *what* the object is, sonar sensors can be used to detect nearby walls or obstacles for navigation during auto.
