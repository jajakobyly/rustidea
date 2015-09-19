#![test]
#![foo = ]
#![test(]
#![cfg(foo=)]
#![foo = "bar"]
#![cfg(foo="bar")]
#![cfg(foo="bar", bar="baz", baz="foo")]
#![cfg(foo, bar, baz)]

//!foo
///foo
#[test]
#[foo = "bar"]
#[cfg(foo="bar", foo="foo")]
#[cfg(foo="bar", bar="baz", baz="foo")]
///moo
#[a=''
#[b='\