package model

import java.time.LocalDate

/**
 * Created by steven on 23/04/15.
 */
case class Allocation(person: Person, taskName: String, executionDate: LocalDate)
