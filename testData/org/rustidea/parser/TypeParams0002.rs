struct Foo<'a, , A, B, C>;
struct Foo<'a, A, B, C,>;
struct Foo< , A, 'x, C, 'b>;

struct Bar< ;
struct Bar<A ;
struct Bar<A, ;
struct Bar<A, B ;