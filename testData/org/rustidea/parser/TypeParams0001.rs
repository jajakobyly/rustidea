struct Foo<'a, 'b, A, B, C>;
struct Foo<'a, A, B, C, 'b>;
struct Foo<'a, A, 'x, C, 'b>;