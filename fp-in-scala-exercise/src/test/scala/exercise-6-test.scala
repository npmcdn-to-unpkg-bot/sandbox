package exercise6

import org.junit._

class Exercise6Test {

  import RNG._

  @Test def exercise6_1_zero: Unit = {
    assert(nonNegativeInt(MockRNG(0))._1 == 0)
  }
  @Test def exercise6_1_one: Unit = {
    assert(nonNegativeInt(MockRNG(1))._1 == 1)
  }
  @Test def exercise6_1_minus_one: Unit = {
    assert(nonNegativeInt(MockRNG(-1))._1 == 1)
  }
  @Test def exercise6_1_max_value: Unit = {
    assert(nonNegativeInt(MockRNG(Int.MaxValue))._1 == Int.MaxValue)
  }
  @Test def exercise6_1_min_value: Unit = {
    assert(nonNegativeInt(MockRNG(Int.MinValue))._1 == 0)
  }

  @Test def exercise6_2_zero: Unit = {
    assert(double(MockRNG(0))._1 == 0.0)
  }
  @Test def exercise6_2_one: Unit = {
    assert(double(MockRNG(Int.MaxValue))._1 == 1.0)
  }

  @Test def exercise6_4: Unit = {
    val (l, r) = ints(3)(MockRNG(1, 2, 3, 4))
    assert(l == List(1, 2, 3))
    assert(r == MockRNG(4))
  }

  @Test def exercise6_6: Unit = {
    val ra: Rand[Int] = _.nextInt
    val rb: Rand[String] = rng => rng.nextInt match {
      case (b, r) => (b.toString, r)
    }
    val rc = map2(ra, rb)((_, _))
    val (l, r) = rc(MockRNG(1, 2, 3))
    assert(l == (1, "2"))
    assert(r == MockRNG(3))
  }

  @Test def exercise6_7: Unit = {
    val ra1: Rand[Int] = _.nextInt
    val ra2: Rand[Int] = rng => rng.nextInt match {
      case (a, rng2) => (a + 1, rng2)
    }
    val ra3: Rand[Int] = rng => rng.nextInt match {
      case (a, rng2) => (a * 2, rng2)
    }
    val fs = List(ra1, ra2, ra3)
    val rl = sequence(fs)
    val rng = MockRNG(1, 3, 5, 7)
    val (l, rng2) = rl(rng)
    assert(l == List(1, 4, 10))
    assert(rng2 == MockRNG(7))
  }

  @Test def exercise6_8_nonNegativeLessThan: Unit = {
    assert(nonNegativeLessThan(12)(MockRNG(15)) == (3, MockRNG()))
  }
  @Test def exercise6_8_nonNegativeLessThan_recursive: Unit = {
    assert(nonNegativeLessThan(3)(MockRNG(Int.MaxValue, 5)) == (2, MockRNG()))
  }
  @Test def exercise6_8_flatMap: Unit = {
    val ra: Rand[Int] = _.nextInt
    val f: Int => Rand[(Int, Int)] = a => rng => rng.nextInt match {
      case (i, rng2) => ((a, i), rng2)
    }
    val rb = flatMap(ra)(f)
    assert(rb(MockRNG(1, 2, 3)) == ((1, 2), MockRNG(3)))
  }

  case class MockRNG(as: Int*) extends RNG {
    def nextInt: (Int, RNG) = (as.head, MockRNG(as.tail: _*))
  }

  @Test def exercise6_11: Unit = {
    val inputs = List.fill(4)(Coin :: Turn :: Nil).flatten
    val s = Machine.simulateMachine(inputs)
    val m1 = Machine(true, 5, 10)
    val (a1, m2) = s.run(m1)
    assert(a1 == (14, 1), a1)
  }
  @Test def exercise6_11_add_coin_to_locked_machine: Unit = {
    val s = Machine.simulateMachine(List(Coin))
    val m1 = Machine(true, 1, 0)
    val (_, m2) = s.run(m1)
    assert(m2 == Machine(false, 1, 1))
  }
  @Test def exercise6_11_turn_handle_unlocked_machine: Unit = {
    val s = Machine.simulateMachine(List(Turn))
    val m1 = Machine(false, 1, 1)
    val (_, m2) = s.run(m1)
    assert(m2 == Machine(true, 0, 1))
  }
  @Test def exercise6_11_add_coin_to_unlocked_machine: Unit = {
    val s = Machine.simulateMachine(List(Coin))
    val m1 = Machine(false, 1, 0)
    val (_, m2) = s.run(m1)
    assert(m2 == Machine(false, 1, 0))
  }
  @Test def exercise6_11_turn_handle_locked_machine: Unit = {
    val s = Machine.simulateMachine(List(Turn))
    val m1 = Machine(true, 1, 1)
    val (_, m2) = s.run(m1)
    assert(m2 == Machine(true, 1, 1))
  }
  @Test def exercise6_11_add_coin_to_sold_out_machine: Unit = {
    val s = Machine.simulateMachine(List(Coin))
    val m1 = Machine(true, 0, 0)
    val (_, m2) = s.run(m1)
    assert(m2 == Machine(true, 0, 0))
  }
  @Test def exercise6_11_turn_handle_sold_out_machine: Unit = {
    val s = Machine.simulateMachine(List(Turn))
    val m1 = Machine(false, 0, 0)
    val (_, m2) = s.run(m1)
    assert(m2 == Machine(false, 0, 0))
  }
}
