struct Func {
    x: fn(i32) -> i32,
    y: Fn(i32) -> i32,
    z: mylib::MyFn(i32, u32, Box<Fn(String, bool) -> (f64, f64)>)
}