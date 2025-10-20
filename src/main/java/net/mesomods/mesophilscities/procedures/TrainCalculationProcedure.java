package net.mesomods.mesophilscities.procedures;

public class TrainCalculationProcedure {
	public static double execute(double x, double z) {
		double xmod1920 = 0;
		double zmod1920 = 0;
		double placement_id = 0;
		xmod1920 = (x % 1920 + 1920) % 1920;
		zmod1920 = (z % 1920 + 1920) % 1920;
		if (xmod1920 % 48 == 0 && zmod1920 % 48 == 0) {
			if ((MainStationDirectionProcedure.execute(Math.round(x / 960) * 960, Math.round(z / 960) * 960)
					? (xmod1920 <= 0 + 48 * LmaxProcedure.execute() || xmod1920 >= 1920 + 48 * LminProcedure.execute()) && (zmod1920 < 0 + 48 * WmaxProcedure.execute() || zmod1920 > 1920 + 48 * WminProcedure.execute())
					: (xmod1920 < 0 + 48 * WmaxProcedure.execute() || xmod1920 > 1920 + 48 * WminProcedure.execute()) && (zmod1920 <= 0 + 48 * LmaxProcedure.execute() || zmod1920 >= 1920 + 48 * LminProcedure.execute()))
					|| (MainStationDirectionProcedure.execute(Math.round(x / 960) * 960, Math.round(z / 960) * 960)
							? xmod1920 <= 960 + 48 * LmaxProcedure.execute() && xmod1920 >= 960 + 48 * LminProcedure.execute() && zmod1920 < 960 + 48 * WmaxProcedure.execute() && zmod1920 > 960 + 48 * WminProcedure.execute()
							: xmod1920 < 960 + 48 * WmaxProcedure.execute() && xmod1920 > 960 + 48 * WminProcedure.execute() && zmod1920 <= 960 + 48 * LmaxProcedure.execute() && zmod1920 >= 960 + 48 * LminProcedure.execute())) {
				if (MainStationDirectionProcedure.execute(Math.round(x / 960) * 960, Math.round(z / 960) * 960)) {
					return -3;
				} else {
					return -12;
				}
			} else {
				if (xmod1920 % 480 != 0 && zmod1920 % 480 != 0) {
					return 0;
				} else {
					if (xmod1920 == 0) {
						if (zmod1920 >= 96 && zmod1920 <= 432) {
							placement_id = MainStationTrackNorthSouthProcedure.execute(x, z);
						} else if (zmod1920 == 480) {
							placement_id = CityBorderCrossingSouthProcedure.execute(x, z);
						} else if (zmod1920 >= 528 && zmod1920 <= 912) {
							placement_id = SimpleCrossingTrackNorthProcedure.execute(x, z);
						} else if (zmod1920 == 960) {
							placement_id = SimpleCrossingDirectionProcedure.execute(x, z);
						} else if (zmod1920 >= 1008 && zmod1920 <= 1392) {
							placement_id = SimpleCrossingTrackSouthProcedure.execute(x, z);
						} else if (zmod1920 == 1440) {
							placement_id = CityBorderCrossingNorthProcedure.execute(x, z);
						} else if (zmod1920 >= 1488 && zmod1920 <= 1824) {
							placement_id = MainStationTrackNorthSouthProcedure.execute(x, z);
						}
					} else if (xmod1920 >= 48 && xmod1920 <= 432) {
						if (zmod1920 == 0) {
							placement_id = MainStationTrackEastWestProcedure.execute(x, z);
						} else if (zmod1920 == 480) {
							placement_id = SpecialCrossingType1TrackEastWestProcedure.execute(x, z);
						} else if (zmod1920 == 960) {
							placement_id = SimpleCrossingTrackEastProcedure.execute(x, z);
						} else if (zmod1920 == 1440) {
							placement_id = SpecialCrossingType2TrackEastWestProcedure.execute(x, z);
						}
					} else if (xmod1920 == 480) {
						if (zmod1920 == 0) {
							placement_id = CityBorderCrossingEastProcedure.execute(x, z);
						} else if (zmod1920 >= 48 && zmod1920 <= 432) {
							placement_id = SpecialCrossingType1TrackNorthSouthProcedure.execute(x, z);
						} else if (zmod1920 == 480) {
							placement_id = SpecialCrossingType1DirectionProcedure.execute(x, z);
						} else if (zmod1920 >= 528 && zmod1920 <= 912) {
							placement_id = SpecialCrossingType1TrackNorthSouthProcedure.execute(x, z);
						} else if (zmod1920 == 960) {
							placement_id = CityBorderCrossingWestProcedure.execute(x, z);
						} else if (zmod1920 >= 1008 && zmod1920 <= 1392) {
							placement_id = SpecialCrossingType2TrackNorthSouthProcedure.execute(x, z);
						} else if (zmod1920 == 1440) {
							placement_id = SpecialCrossingType2DirectionProcedure.execute(x, z);
						} else if (zmod1920 >= 1488 && zmod1920 <= 1872) {
							placement_id = SpecialCrossingType2TrackNorthSouthProcedure.execute(x, z);
						}
					} else if (xmod1920 >= 528 && xmod1920 <= 912) {
						if (zmod1920 == 0) {
							placement_id = SimpleCrossingTrackWestProcedure.execute(x, z);
						} else if (zmod1920 == 480) {
							placement_id = SpecialCrossingType1TrackEastWestProcedure.execute(x, z);
						} else if (zmod1920 == 960) {
							placement_id = MainStationTrackEastWestProcedure.execute(x, z);
						} else if (zmod1920 == 1440) {
							placement_id = SpecialCrossingType2TrackEastWestProcedure.execute(x, z);
						}
					} else if (xmod1920 == 960) {
						if (zmod1920 == 0) {
							placement_id = SimpleCrossingDirectionProcedure.execute(x, z);
						} else if (zmod1920 >= 48 && zmod1920 <= 432) {
							placement_id = SimpleCrossingTrackSouthProcedure.execute(x, z);
						} else if (zmod1920 == 480) {
							placement_id = CityBorderCrossingNorthProcedure.execute(x, z);
						} else if (zmod1920 >= 528 && zmod1920 <= 864) {
							placement_id = MainStationTrackNorthSouthProcedure.execute(x, z);
						} else if (zmod1920 >= 1056 && zmod1920 <= 1392) {
							placement_id = MainStationTrackNorthSouthProcedure.execute(x, z);
						} else if (zmod1920 == 1440) {
							placement_id = CityBorderCrossingSouthProcedure.execute(x, z);
						} else if (zmod1920 >= 1488 && zmod1920 <= 1872) {
							placement_id = SimpleCrossingTrackNorthProcedure.execute(x, z);
						}
					} else if (xmod1920 >= 1008 && xmod1920 <= 1392) {
						if (zmod1920 == 0) {
							placement_id = SimpleCrossingTrackEastProcedure.execute(x, z);
						} else if (zmod1920 == 480) {
							placement_id = SpecialCrossingType2TrackEastWestProcedure.execute(x, z);
						} else if (zmod1920 == 960) {
							placement_id = MainStationTrackEastWestProcedure.execute(x, z);
						} else if (zmod1920 == 1440) {
							placement_id = SpecialCrossingType1TrackEastWestProcedure.execute(x, z);
						}
					} else if (xmod1920 == 1440) {
						if (zmod1920 == 0) {
							placement_id = CityBorderCrossingWestProcedure.execute(x, z);
						} else if (zmod1920 >= 48 && zmod1920 <= 432) {
							placement_id = SpecialCrossingType2TrackNorthSouthProcedure.execute(x, z);
						} else if (zmod1920 == 480) {
							placement_id = SpecialCrossingType2DirectionProcedure.execute(x, z);
						} else if (zmod1920 >= 528 && zmod1920 <= 912) {
							placement_id = SpecialCrossingType2TrackNorthSouthProcedure.execute(x, z);
						} else if (zmod1920 == 960) {
							placement_id = CityBorderCrossingEastProcedure.execute(x, z);
						} else if (zmod1920 >= 1008 && zmod1920 <= 1392) {
							placement_id = SpecialCrossingType1TrackNorthSouthProcedure.execute(x, z);
						} else if (zmod1920 == 1440) {
							placement_id = SpecialCrossingType1DirectionProcedure.execute(x, z);
						} else if (zmod1920 >= 1488 && zmod1920 <= 1872) {
							placement_id = SpecialCrossingType1TrackNorthSouthProcedure.execute(x, z);
						}
					} else if (xmod1920 >= 1488 && xmod1920 <= 1872) {
						if (zmod1920 == 0) {
							placement_id = MainStationTrackEastWestProcedure.execute(x, z);
						} else if (zmod1920 == 480) {
							placement_id = SpecialCrossingType2TrackEastWestProcedure.execute(x, z);
						} else if (zmod1920 == 960) {
							placement_id = SimpleCrossingTrackWestProcedure.execute(x, z);
						} else if (zmod1920 == 1440) {
							placement_id = SpecialCrossingType1TrackEastWestProcedure.execute(x, z);
						}
					}
					if (placement_id != 0) {
						return placement_id;
					} else {
						return 0;
					}
				}
			}
		}
		return 0;
	}
}
