struct A {
    x: &i32,
    y: &'static str,

    p: *const foo::Bar<f32>,
    q: *mut (i32, i32),

    slice: &[usize]
}