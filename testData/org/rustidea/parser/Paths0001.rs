type Me = Self;
type MeToo = Self::foo::bar;
type MeAlso = Self<i32>;
type MeFunc = Self(i32) -> i32;
type MeFunc2 = Self<i32>(i32) -> i32;
type NotMeButSelfModule = foo::bar::Self;