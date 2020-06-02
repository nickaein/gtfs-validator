/*
 * Copyright (c) 2020. MobilityData IO.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mobilitydata.gtfsvalidator.domain.entity.gtfs;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mobilitydata.gtfsvalidator.domain.entity.notice.base.Notice;
import org.mobilitydata.gtfsvalidator.domain.entity.notice.error.FloatFieldValueOutOfRangeNotice;
import org.mobilitydata.gtfsvalidator.domain.entity.notice.error.IntegerFieldValueOutOfRangeNotice;
import org.mobilitydata.gtfsvalidator.domain.entity.notice.error.MissingRequiredValueNotice;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for all entities defined in shapes.txt. Can not be directly instantiated: user must use the
 * {@link Shape.ShapeBuilder} to create this.
 */
public class Shape extends GtfsEntity implements Comparable<Shape> {
    @NotNull
    private final String shapeId;
    private final float shapePtLat;
    private final float shapePtLon;
    private final int shapePtSequence;
    @Nullable
    private final Float shapeDistTraveled;

    /**
     * Class for all entities defined in shapes.txt
     *
     * @param shapeId           identifies a shape
     * @param shapePtLat        latitude of a shape point
     * @param shapePtLon        longitude of a shape point
     * @param shapePtSequence   sequence in which the shape points connect to form the shape
     * @param shapeDistTraveled actual distance traveled along the shape from the first shape point to the point
     *                          specified in this record
     */
    private Shape(@NotNull final String shapeId,
                  float shapePtLat,
                  float shapePtLon,
                  final int shapePtSequence,
                  @Nullable final Float shapeDistTraveled) {
        this.shapeId = shapeId;
        this.shapePtLat = shapePtLat;
        this.shapePtLon = shapePtLon;
        this.shapePtSequence = shapePtSequence;
        this.shapeDistTraveled = shapeDistTraveled;
    }

    @NotNull
    public String getShapeId() {
        return shapeId;
    }

    public float getShapePtLat() {
        return shapePtLat;
    }

    public float getShapePtLon() {
        return shapePtLon;
    }

    @NotNull
    public Integer getShapePtSequence() {
        return shapePtSequence;
    }

    @Nullable
    public Float getShapeDistTraveled() {
        return shapeDistTraveled;
    }

    /**
     * Implement compareTo method from {@code Comparable}. Return the value {@code 0} if this {@code Shape} field
     * shape_pt_sequence is equal to field shape_pt_sequence of argument; a value less than {@code 0} if this field
     * shape_pt_sequence is numerically less than field shape_pt_sequence of argument; and a value greater
     * than {@code 0} if this field shape_pt_sequence is numerically greater than field shape_pt_sequence of argument
     *
     * @param shape shape to compare to
     * @return the value {@code 0} if this {@code Shape} field shape_pt_sequence is equal to field shape_pt_sequence of
     * argument; a value less than {@code 0} if this field shape_pt_sequence is numerically less than field
     * shape_pt_sequence of argument; and a value greater than {@code 0} if this field shape_pt_sequence is numerically
     * greater than field shape_pt_sequence of argument
     */
    @Override
    public int compareTo(@NotNull final Shape shape) {
        return getShapePtSequence().compareTo(shape.getShapePtSequence());
    }

    /**
     * Return true if this {@link Shape} has field shape_pt_sequence greater than {@param otherShape}
     * field shape_pt_sequence; otherwise return false
     *
     * @param otherShape shape to compare
     * @return true if this {@link Shape} has field shape_pt_sequence greater than {@param otherShape}
     * field shape_pt_sequence; otherwise return false
     */
    public boolean isGreaterThan(final Shape otherShape) {
        return compareTo(otherShape) > 0;
    }

    /**
     * Builder class to create {@link Shape} objects. Allows an unordered definition of the different attributes of
     * {@link Shape}.
     */
    public static class ShapeBuilder {
        private String shapeId;
        private Float shapePtLat;
        private Float shapePtLon;
        private Integer shapePtSequence;
        private final List<Notice> noticeCollection = new ArrayList<>();
        @Nullable
        private Float shapeDistTraveled;

        /**
         * Sets field shapeId value and returns this
         *
         * @param shapeId identifies a shape
         * @return builder for future object creation
         */
        public ShapeBuilder shapeId(@NotNull final String shapeId) {
            this.shapeId = shapeId;
            return this;
        }

        /**
         * Sets field shapePtLat value and returns this
         *
         * @param shapePtLat latitude of a shape point
         * @return builder for future object creation
         */
        public ShapeBuilder shapePtLat(@NotNull final Float shapePtLat) {
            this.shapePtLat = shapePtLat;
            return this;
        }

        /**
         * Sets field shapePtLon value and returns this
         *
         * @param shapePtLon longitude of a shape point
         * @return builder for future object creation
         */
        public ShapeBuilder shapePtLon(@NotNull final Float shapePtLon) {
            this.shapePtLon = shapePtLon;
            return this;
        }

        /**
         * Sets field shapePtSequence value and returns this
         *
         * @param shapePtSequence sequence in which the shape points connect to form the shape
         * @return builder for future object creation
         */
        public ShapeBuilder shapePtSequence(@NotNull final Integer shapePtSequence) {
            this.shapePtSequence = shapePtSequence;
            return this;
        }

        /**
         * Sets field shapeDistTraveled value and returns this
         *
         * @param shapeDistTraveled actual distance traveled along the shape from the first shape point to the point
         *                          specified in this record
         * @return builder for future object creation
         */
        public ShapeBuilder shapeDistTraveled(@Nullable final Float shapeDistTraveled) {
            this.shapeDistTraveled = shapeDistTraveled;
            return this;
        }

        /**
         * Entity representing a row from shapes.txt if the requirements from the official GTFS specification
         * are met. Otherwise, method returns an entity representing a list of notices.
         *
         * @return Entity representing a row from shapes.txt if the requirements from the official GTFS specification
         * are met. Otherwise, method returns an entity representing a list of notices.
         */
        public EntityBuildResult<?> build() {
            noticeCollection.clear();

            //noinspection ConstantConditions to avoid lint
            if (shapeId == null ||
                    shapePtLat == null || (shapePtLat != null && (shapePtLat < -90.0f || shapePtLat > 90.0f)) ||
                    shapePtLon == null || (shapePtLon != null && (shapePtLon < -180.0f || shapePtLon > 180.0f)) ||
                    shapePtSequence == null || (shapePtSequence != null && shapePtSequence < 0) ||
                    (shapeDistTraveled != null && shapeDistTraveled < 0)) {
                if (shapeId == null) {
                    noticeCollection.add(new MissingRequiredValueNotice("shapes.txt", "shape_id",
                            shapeId));
                }
                if (shapePtLat == null) {
                    noticeCollection.add(new MissingRequiredValueNotice("shapes.txt", "shape_pt_lat",
                            shapeId));
                } else if (shapePtLat < -90.0f || shapePtLat > 90.0f) {
                    noticeCollection.add(new FloatFieldValueOutOfRangeNotice("shapes.txt",
                            "shape_pt_lat", shapeId, -90.0f, 90.0f, shapePtLat));
                }
                if (shapePtLon == null) {
                    noticeCollection.add(new MissingRequiredValueNotice("shapes.txt", "shape_pt_lon",
                            shapeId));
                } else if (shapePtLon < -180.0f || shapePtLon > 180.0f) {
                    noticeCollection.add(new FloatFieldValueOutOfRangeNotice("shapes.txt",
                            "shape_pt_lon", shapeId, -180.0f, 180.0f, shapePtLon));
                }
                if (shapePtSequence == null) {
                    noticeCollection.add(new MissingRequiredValueNotice("shapes.txt", "shape_pt_sequence",
                            shapeId));
                } else if (shapePtSequence < 0) {
                    noticeCollection.add(new IntegerFieldValueOutOfRangeNotice("shapes.txt",
                            "shape_pt_sequence", shapeId, 0, Integer.MAX_VALUE, shapePtSequence));
                }
                if (shapeDistTraveled != null && shapeDistTraveled < 0) {
                    noticeCollection.add(new FloatFieldValueOutOfRangeNotice("shapes.txt",
                            "shape_dist_traveled", shapeId, 0, Float.MAX_VALUE, shapeDistTraveled));
                }
                return new EntityBuildResult<>(noticeCollection);
            } else {
                return new EntityBuildResult<>(new Shape(shapeId, shapePtLat, shapePtLon, shapePtSequence, shapeDistTraveled));
            }
        }
    }
}