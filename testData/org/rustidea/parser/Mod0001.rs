/// doc
pub mod foo {
    //! doc2
    #[test]
    extern crate moo;
    pub mod bar;
}

mod moo;